package com.meiji.elegantcommuncity.model;


import com.meiji.elegantcommuncity.util.Uuid;

/**
 * Created by zzc on 2017/10/29.
 */

public class Header {
    private String requestId;
    private String conversationId;
    public Header(){
        requestId = Uuid.getUUID();
        conversationId=Uuid.getUUID();
    }
}
