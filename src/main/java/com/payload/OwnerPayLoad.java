package com.payload;

public class OwnerPayLoad {
        private String username;
        private String name;
        private String email;
        private String mobileno;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public OwnerPayLoad(String username, String name, String email, String mobileno) {
            this.username = username;
            this.name = name;
            this.email = email;
            this.mobileno = mobileno;
        }
}
