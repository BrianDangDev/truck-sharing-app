package com.data.trucksharingapp.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.data.trucksharingapp.api.ApiConfiguration;
import com.data.trucksharingapp.R;
import com.data.trucksharingapp.RecyclerAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatBotActivity extends AppCompatActivity {

    // declaration
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private CheckBox check;
    private EditText userMsgEdt, fromLoc;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    private RequestQueue mRequestQueue;

    private ApiConfiguration api;

    private ArrayList<MessageModal> messageModalArrayList;
    private RecyclerAdapter messageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        // initialization
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        fromLoc = findViewById(R.id.pickup_location);
        check = findViewById(R.id.checkBox);

        mRequestQueue = Volley.newRequestQueue(ChatBotActivity.this);
        mRequestQueue.getCache().clear();

        messageModalArrayList = new ArrayList<>();

        api = new ApiConfiguration();

        // when user checks location distances
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check.isChecked()) {
                    // if user doesn't type anything but they pressed enter
                    if (userMsgEdt.getText().toString().isEmpty() || fromLoc.getText().toString().isEmpty()) {
                        Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    } else {
                        // send the message after the user enters the location
                        sendMessage(userMsgEdt.getText().toString(), userMsgEdt.getText().toString(), fromLoc.getText().toString());
                    }
                } else {
                    // if user doesn't type anything but they pressed enter
                    if (userMsgEdt.getText().toString().isEmpty()) {
                        Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    } else {
                        sendMessage(userMsgEdt.getText().toString(), userMsgEdt.getText().toString(), fromLoc.getText().toString());
                    }
                }
                userMsgEdt.setText("");
                fromLoc.setText("");
            }
        });

        // if the user checks location distances
        // user needs to enter the drop-off location and pickup location
        check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fromLoc.setVisibility(View.VISIBLE);
                userMsgEdt.setHint("Drop-off Location");
            } else {
                fromLoc.setVisibility(View.GONE);
                userMsgEdt.setHint("Enter Message");
            }
        });

        // using recycler adapter for the chat between the bot and the user
        messageRVAdapter = new RecyclerAdapter(messageModalArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBotActivity.this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
    }

    // reference: https://stackoverflow.com/questions/71553254/how-to-flow-data-from-one-recyclerview-to-another-recyclerview-when-an-item-in-f
    // to show the destination based on the user input (from-to)
    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage(String userMsg, String origin, String destination) {
        if (check.isChecked()) {
            String message = origin + " - " + destination;
            messageModalArrayList.add(new MessageModal(message, USER_KEY));
        } else {
            messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        }
        messageRVAdapter.notifyDataSetChanged();

        // api used for this project
        // sources: https://brainshop.ai/
        String url = "http://api.brainshop.ai/get?bid=166965&key=huLURXt41yco2anH&uid=[uid]&msg=" + userMsg;

        // resource: https://stackoverflow.com/questions/32795970/how-to-make-a-new-newrequestqueue-in-volley-android
        // to stack users' requests
        RequestQueue queue = Volley.newRequestQueue(ChatBotActivity.this);

        // to check message inputted by the user; if the user asks questions about the truck availibility, the bot will answer with yes/or
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String botResponse = response.getString("cnt");
                if (botResponse.equals("truck")) {
                    Random random = new Random();
                    int check = random.nextInt(2);
                    String message = "";
                    if (check == 0) {
                        message = "Yes";
                    } else {
                        message = "No";
                    }
                    messageModalArrayList.add(new MessageModal(message, BOT_KEY));

                    // if the user asks a random question/beyond the bot's capabilities, the bot will reply with "sorry i don't quite get it"
                } else {
                    if (check.isChecked()) {
                        getDistance(origin, destination);
                    } else {
                        messageModalArrayList.add(new MessageModal("sorry i don't quite get it", BOT_KEY));
                    }
                }

                messageRVAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();

                messageModalArrayList.add(new MessageModal("No response", BOT_KEY));
                messageRVAdapter.notifyDataSetChanged();
            }
            // error msg
        }, error -> {
            messageModalArrayList.add(new MessageModal(error.getMessage(), BOT_KEY));
            Toast.makeText(ChatBotActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
        });

        queue.add(jsonObjectRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getDistance(String origin, String destination) {
        api.getApi().getDistance(getString(R.string.api_key), origin, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResultDistance>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    // Displays distance calculation from user's pickup loc and dropoff loc
                    @Override
                    public void onSuccess(@NonNull ResultDistance result) {
                        String distance = result.getRows().get(0).getElements().get(0).getDistance().getText();
                        messageModalArrayList.add(new MessageModal("Distance From " + destination + " to " + origin + ": " + distance, BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        messageModalArrayList.add(new MessageModal("Sorry i don't quite get it", BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                    }
                });
    }
}