package com.livecook.livecookapp.Model;
import java.util.List;

import java.util.List;

public class Countries {

    private Boolean status;
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}