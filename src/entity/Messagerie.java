/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;





/**
 *
 * @author User
 */
public class Messagerie {
    private Integer id;
    private String ContenuMsg;
    private String dateHeureEnvoi; 
    private int sender_id;
    private int reciever_id;
    private boolean deletedSender;
    private boolean deletedReciever;

    public Messagerie() {
    }

    public Messagerie(String ContenuMsg, int sender_id, int reciever_id, boolean deletedSender, boolean deletedReciever) {
        this.ContenuMsg = ContenuMsg;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.deletedSender = deletedSender;
        this.deletedReciever = deletedReciever;
    }

    public Messagerie(String ContenuMsg, int sender_id, int reciever_id) {
        this.ContenuMsg = ContenuMsg;
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenuMsg() {
        return ContenuMsg;
    }

    public void setContenuMsg(String ContenuMsg) {
        this.ContenuMsg = ContenuMsg;
    }

    public String getDateHeureEnvoi() {
        return dateHeureEnvoi;
    }

    public void setDateHeureEnvoi(String dateHeureEnvoi) {
        this.dateHeureEnvoi = dateHeureEnvoi;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(int reciever_id) {
        this.reciever_id = reciever_id;
    }

    public boolean isDeletedSender() {
        return deletedSender;
    }

    public void setDeletedSender(boolean deletedSender) {
        this.deletedSender = deletedSender;
    }

    public boolean isDeletedReciever() {
        return deletedReciever;
    }

    public void setDeletedReciever(boolean deletedReciever) {
        this.deletedReciever = deletedReciever;
    }
    

  

   

   
   
//    public JSONObject toJSON() {
//        JSONObject obj = createJSONObject
//        ("fromId", sender_id,
//                "toId", reciever_id,
////                "name", name,
////                "pic", picture,
//                "time", String.toString(System.currentTimeMillis()),
//                "message", ContenuMsg);
//        return obj;
//    }

    /**
     * Helper method to create a JSONObject
     */
//    JSONObject createJSONObject(String... keyValues) {
//        try {
//            JSONObject o = new JSONObject();
//            for(int iter = 0 ; iter < keyValues.length ; iter += 2) {
//                o.put(keyValues[iter], keyValues[iter + 1]);
//            }
//            return o;
//        } catch(JSONException err) {
//            // will this ever happen?
//            err.printStackTrace();
//        }
//        return null;
//    }


   
    
   
    
}
