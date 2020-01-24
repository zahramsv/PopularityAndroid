package com.example.popularity.utils.sms;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.Random;

import ir.trez.raygansms.Raygansms;
import ir.trez.raygansms.Result;
import ir.trez.raygansms.ResultCode;

@SuppressLint("StaticFieldLeak")
public class SmsHandler {

    public SmsHandler(SmsHandlerListener listener){
        this.listener = listener;
    }
    private SmsHandlerListener listener;
    private Raygansms raygansms = new Raygansms("mohadi", "m@xRaygansms");
    private String[] userMobile = { "" };
    private final static  String smsPanelPhoneNumber = "50002210003000";
    private String verifyCode = "xxxx";

    private class AuthSms extends AsyncTask<Void, Void, Result> {
        @Override
        protected void onPreExecute() { }
        @Override
        protected Result doInBackground(Void... voids) {
            try {
                return raygansms.GetCredit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if(result != null) {
                if(result.getCode() == ResultCode.Success){
                    new SendSms().execute();
                }else{
                    listener.onSmsSendingResult(false, "authentication error happened to send sms! please try later.");
                }
            }
        }
    }

    private class SendSms extends AsyncTask<Void, Void, Result> {

        @Override
        protected void onPreExecute() { }
        @Override
        protected Result doInBackground(Void... voids) {
            try {
                return raygansms.SendMessage(smsPanelPhoneNumber, getMessageToSend(), userMobile, getUserGroupID(), System.currentTimeMillis() / 1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if(result != null) {
                if(result.getCode() == ResultCode.Success)
                    listener.onSmsSendingResult(true, "sms sent successfully.");
                else
                    listener.onSmsSendingResult(false, result.getMessage());
            }
        }
    }

    public void requestSendSms(String userPhone){
        userMobile[0] = userPhone;
        generateValidationCode();
        listener.onSmsSendingResult(true, "sms: "+verifyCode);
       // new AuthSms().execute();
    }

    public Boolean isVerifyCodeValid(String code){
        code = code.replaceAll("\\s+","");
        return verifyCode.equals(code);
    }

    public interface SmsHandlerListener{
        void onSmsSendingResult(Boolean isSuccess, String message);
    }

    private String getMessageToSend(){
        return "سلام , شماره عضویت شما در پاپیولاریتی : "+generateValidationCode();
    }

    private String generateValidationCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=0;i<4;i++){
            code.append(r.nextInt(10 - 1) + 1);
        }
        verifyCode = code.toString();
        return verifyCode;
    }

    private String getUserGroupID(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=0;i<6;i++){
            code.append(r.nextInt(10 - 1) + 1);
        }
        return code.toString();
    }

}
