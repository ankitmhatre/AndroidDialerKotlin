package com.am.service;

import android.bluetooth.BluetoothDevice;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.os.Bundle;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyConnection extends Connection {





    @Override
    public void requestBluetoothAudio(@NonNull BluetoothDevice bluetoothDevice) {
        super.requestBluetoothAudio(bluetoothDevice);
    }

    @Override
    public void onCallAudioStateChanged(CallAudioState state) {
        super.onCallAudioStateChanged(state);
    }

    @Override
    public void onStateChanged(int state) {
        super.onStateChanged(state);
    }

    @Override
    public void onPlayDtmfTone(char c) {
        super.onPlayDtmfTone(c);
    }

    @Override
    public void onStopDtmfTone() {
        super.onStopDtmfTone();
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
    }

    @Override
    public void onSeparate() {
        super.onSeparate();
    }

    @Override
    public void onAbort() {
        super.onAbort();
    }

    @Override
    public void onHold() {
        super.onHold();
    }

    @Override
    public void onUnhold() {
        super.onUnhold();
    }

    @Override
    public void onAnswer(int videoState) {
        super.onAnswer(videoState);
    }

    @Override
    public void onAnswer() {
        super.onAnswer();
    }

    @Override
    public void onDeflect(Uri address) {
        super.onDeflect(address);
    }

    @Override
    public void onReject() {
        super.onReject();
    }

    @Override
    public void onReject(String replyMessage) {
        super.onReject(replyMessage);
    }

    @Override
    public void onPostDialContinue(boolean proceed) {
        super.onPostDialContinue(proceed);
    }

    @Override
    public void onPullExternalCall() {
        super.onPullExternalCall();
    }

    @Override
    public void onCallEvent(String event, Bundle extras) {
        super.onCallEvent(event, extras);
    }

    @Override
    public void onHandoverComplete() {
        super.onHandoverComplete();
    }

    @Override
    public void onExtrasChanged(Bundle extras) {
        super.onExtrasChanged(extras);
    }

    @Override
    public void onShowIncomingCallUi() {
        super.onShowIncomingCallUi();
    }

    @Override
    public void onStartRtt(@NonNull RttTextStream rttTextStream) {
        super.onStartRtt(rttTextStream);
    }

    @Override
    public void onStopRtt() {
        super.onStopRtt();
    }

    @Override
    public void handleRttUpgradeResponse(@Nullable RttTextStream rttTextStream) {
        super.handleRttUpgradeResponse(rttTextStream);
    }

    @Override
    public void sendConnectionEvent(String event, Bundle extras) {
        super.sendConnectionEvent(event, extras);
    }

}
