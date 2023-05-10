package com.data.trucksharingapp.picklocation;

import com.data.trucksharingapp.OverviewPolyline;

import java.util.List;

public class Route {
    private List<Legs> legs;

    private OverviewPolyline overview_polyline;

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overview_polyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overview_polyline = overviewPolyline;
    }

    public class Duration{
        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public class Legs{
        private Duration duration;

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }
}
