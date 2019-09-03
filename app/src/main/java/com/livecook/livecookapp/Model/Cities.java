package com.livecook.livecookapp.Model;

import java.util.List;

public class Cities {


    /**
     * status : true
     * data : [{"id":2746,"name":"أريحا"},{"id":2761,"name":"الخليل"},{"id":2762,"name":"القدس"},{"id":2747,"name":"بيت لحم"},{"id":2752,"name":"جنين"},{"id":2753,"name":"خانيونس"},{"id":2749,"name":"دير البلح"},{"id":2757,"name":"رام الله والبيرة"},{"id":2756,"name":"رفح"},{"id":2758,"name":"سلفيت"},{"id":2751,"name":"شمال غزة"},{"id":2759,"name":"طوباس"},{"id":2760,"name":"طولكرم"},{"id":2750,"name":"غزة"},{"id":2755,"name":"قلقيلية"},{"id":2754,"name":"نابلس"}]
     */

    private boolean status;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2746
         * name : أريحا
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
