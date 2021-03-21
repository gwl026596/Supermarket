package com.fintek.httprequestlibrary.api.response;

public class OcrRespomse {

    /**
     * userId : 327603
     * success : true
     * idCardData : {"nik":"3529016807960001","name":"NUR MAULINA FAJRIN","bloodType":"A","address":"JL. TAMAN SISWA NO.29"}
     */

    private int userId;
    private boolean success;
    private IdCardDataBean idCardData;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public IdCardDataBean getIdCardData() {
        return idCardData;
    }

    public void setIdCardData(IdCardDataBean idCardData) {
        this.idCardData = idCardData;
    }

    public static class IdCardDataBean {
        /**
         * nik : 3529016807960001
         * name : NUR MAULINA FAJRIN
         * bloodType : A
         * address : JL. TAMAN SISWA NO.29
         */

        private String nik;
        private String name;
        private String bloodType;
        private String address;

        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
