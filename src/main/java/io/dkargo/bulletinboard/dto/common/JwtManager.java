//package io.dkargo.bulletinboard.dto.common;
//
//import io.jsonwebtoken.Jwt;
//import io.jsonwebtoken.impl.crypto.MacSigner;
//
//public class JwtManager {
//    private static final MacSigner macSigner = new MacSigner("will-b-fine");
//    private static final Gson gson = new Gson();
//
//    public static Jwt createJwt(String id){
//
//        return JwtHelper.encode(createPayload(id), macSigner);
//    }
//
//    private static String createPayload(String id){
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("id", id);
//        jsonObject.addProperty("iat",getIssueAt());
//
//        return gson.toJson(jsonObject);
//    }
//
//    private static long getIssueAt(){
//        return System.currentTimeMillis();
//    }
//}
