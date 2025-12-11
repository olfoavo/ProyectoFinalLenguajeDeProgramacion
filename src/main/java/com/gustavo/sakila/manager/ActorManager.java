package com.gustavo.sakila.manager;

import com.gustavo.sakila.dao.ActorDao;
import com.gustavo.sakila.dao.impl.ActorDaoJdbc;
import com.gustavo.sakila.entity.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorManager {
    private final ActorDao dao = new ActorDaoJdbc();
    private final List<Actor> cache = new ArrayList<>();

    public ActorManager(){ cache.addAll(dao.getAll()); }

    public Actor post(Actor a){
        Actor created = dao.post(a);
        cache.add(created);
        return created;
    }

    public Optional<Actor> get(int id){
        return cache.stream().filter(x -> x.getActorId()==id).findFirst()
                .or(() -> dao.getById(id));
    }

    public List<Actor> getAll(){ return new ArrayList<>(cache); }

    public Actor put(Actor a){
        Actor updated = dao.put(a);
        cache.replaceAll(x -> x.getActorId().equals(a.getActorId()) ? updated : x);
        return updated;
    }

    public boolean delete(int id){
        boolean ok = dao.delete(id);
        if (ok) cache.removeIf(x -> x.getActorId()==id);
        return ok;
    }
}
