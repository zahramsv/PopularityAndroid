package com.example.popularity.utils.sms;

import android.os.AsyncTask;
import android.os.Handler;

import ir.trez.raygansms.Raygansms;
import ir.trez.raygansms.RecipientsMessage;
import ir.trez.raygansms.Result;

public class SmsHandler {

    public SmsHandler(SmsHandlerListener listener){
        this.listener = listener;
    }
    private SmsHandlerListener listener;
    private Raygansms raygansms = new Raygansms("mohadi", "m@xRaygansms");
    private String[] userMobile = { "09352775527" };
    private RecipientsMessage[] recipientsMessages = {};
    private String[] MessageIDs = { "1" };
    private String smsPanelPhoneNumber = "50002210003000";
    private String userGroupID = "1";

    private class CallSMS extends AsyncTask<Void, Void, Result> {
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
                String text = "Code:\t" + result.getCode() + "\nMessage:\t" + result.getMessage();
                if(result.getResult() != null){
                    text += "\nResult:\t" + result.getResult();
                }
                listener.onLoginDone(text);
            }
        }
    }

    private class SendSms extends AsyncTask<Void, Void, Result> {
        @Override
        protected void onPreExecute() { }
        @Override
        protected Result doInBackground(Void... voids) {
            try {
                return raygansms.SendMessage(smsPanelPhoneNumber, getMessageToSend(), userMobile, userGroupID, System.currentTimeMillis() / 1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Result result) {
            if(result != null) {
                String text = "Code:\t" + result.getCode() + "\nMessage:\t" + result.getMessage();
                if(result.getResult() != null){
                    text += "\nResult:\t" + result.getResult();
                }
                listener.onSmsSend(text);
            }
        }
    }


    public void auth(){
        new CallSMS().execute();
    }

    public void requestSendSms(){
        new SendSms().execute();
    }

    public interface SmsHandlerListener{
        void onLoginDone(String result);
        void onSmsSend(String result);
    }

    private String getMessageToSend(){
        return "سلام , شماره عضویت شما : "+generateValidationCode();
    }

    private String generateValidationCode(){
        String verifyCode = "6651";
        return  verifyCode;
    }

}
