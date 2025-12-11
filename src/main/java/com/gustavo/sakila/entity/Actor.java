package com.gustavo.sakila.entity;

public class Actor extends BaseEntity<Integer> {
    private Integer actorId;
    private String firstName;
    private String lastName;

    @Override public Integer getId(){ return actorId; }
    @Override public void setId(Integer id){ this.actorId = id; }
    public Integer getActorId(){ return actorId; }
    public void setActorId(Integer id){ this.actorId = id; }
    public String getFirstName(){ return firstName; }
    public void setFirstName(String fn){ this.firstName = fn; }
    public String getLastName(){ return lastName; }
    public void setLastName(String ln){ this.lastName = ln; }

    @Override public String toString(){
        return "Actor{id=%d, first='%s', last='%s'}".formatted(actorId, firstName, lastName);
    }
}
