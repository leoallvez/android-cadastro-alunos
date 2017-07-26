package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;

/**
 * Created by android6920 on 25/07/17.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle budle = intent.getExtras();

        Object[] pdus = (Object[]) budle.get("pdus");
        byte[] pdu = (byte[]) pdus[0];
        SmsMessage sms = SmsMessage.createFromPdu(pdu);
        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.isAluno(telefone)) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);

            mp.start();

            Toast.makeText(context, "Chegou SMS: " + sms.getMessageBody(), Toast.LENGTH_LONG).show();
        }

    }
}

