package com.livecook.livecookapp.Model;

import java.util.List;

public class Allcountries {


    /**
     * status : true
     * data : [{"id":111,"name":"الأردن","nationality":"أردني","sort_name":"JO","code":"962","flag":"https://livecook.co/img/JO.png"},{"id":229,"name":"الإمارات","nationality":"إماراتي","sort_name":"AE","code":"971","flag":"https://livecook.co/img/AE.png"},{"id":17,"name":"البحرين","nationality":"بحريني","sort_name":"BH","code":"973","flag":"https://livecook.co/img/BH.png"},{"id":3,"name":"الجزائر","nationality":"جزائري","sort_name":"DZ","code":"213","flag":"https://livecook.co/img/DZ.png"},{"id":191,"name":"السعودية","nationality":"سعودي","sort_name":"SA","code":"966","flag":"https://livecook.co/img/SA.png"},{"id":207,"name":"السودان","nationality":"سوداني","sort_name":"SD","code":"249","flag":"https://livecook.co/img/SD.png"},{"id":201,"name":"الصومال","nationality":"صومالي","sort_name":"SO","code":"252","flag":"https://livecook.co/img/SO.png"},{"id":104,"name":"العِراق","nationality":"عراقي","sort_name":"IQ","code":"964","flag":"https://livecook.co/img/IQ.png"},{"id":117,"name":"الكويت","nationality":"كويتي","sort_name":"KW","code":"965","flag":"https://livecook.co/img/KW.png"},{"id":148,"name":"المَغرب","nationality":"مغربي","sort_name":"MA","code":"212","flag":"https://livecook.co/img/MA.png"},{"id":243,"name":"اليمن","nationality":"يمني","sort_name":"YE","code":"967","flag":"https://livecook.co/img/YE.png"},{"id":222,"name":"تونس","nationality":"تونسي","sort_name":"TN","code":"216","flag":"https://livecook.co/img/TN.png"},{"id":48,"name":"جُزر القمر","nationality":"جُزر القمر","sort_name":"KM","code":"269","flag":"https://livecook.co/img/KM.png"},{"id":204,"name":"جنوب السودان","nationality":"جنوب السودان","sort_name":"SS","code":"211","flag":"https://livecook.co/img/SS.png"},{"id":59,"name":"جيبوتي","nationality":"جيبوتي","sort_name":"DJ","code":"253","flag":"https://livecook.co/img/DJ.png"},{"id":213,"name":"سوريا","nationality":"سوري","sort_name":"SY","code":"963","flag":"https://livecook.co/img/SY.png"},{"id":165,"name":"عُمان","nationality":"عُماني","sort_name":"OM","code":"968","flag":"https://livecook.co/img/OM.png"},{"id":168,"name":"فلسطين","nationality":"فلسطيني","sort_name":"PS","code":"970","flag":"https://livecook.co/img/PS.png"},{"id":178,"name":"قَطر","nationality":"قطري","sort_name":"QA","code":"974","flag":"https://livecook.co/img/QA.png"},{"id":121,"name":"لبنان","nationality":"لبناني","sort_name":"LB","code":"961","flag":"https://livecook.co/img/LB.png"},{"id":124,"name":"ليبيا","nationality":"ليبي","sort_name":"LY","code":"218","flag":"https://livecook.co/img/LY.png"},{"id":64,"name":"مصر","nationality":"مصري","sort_name":"EG","code":"20","flag":"https://livecook.co/img/EG.png"},{"id":139,"name":"موريتانيا","nationality":"موريتاني","sort_name":"MR","code":"222","flag":"https://livecook.co/img/MR.png"}]
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
         * id : 111
         * name : الأردن
         * nationality : أردني
         * sort_name : JO
         * code : 962
         * flag : https://livecook.co/img/JO.png
         */

        private int id;
        private String name;
        private String nationality;
        private String sort_name;
        private String code;
        private String flag;

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

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getSort_name() {
            return sort_name;
        }

        public void setSort_name(String sort_name) {
            this.sort_name = sort_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
