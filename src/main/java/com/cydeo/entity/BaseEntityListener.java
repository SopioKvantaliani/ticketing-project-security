package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class BaseEntityListener extends AuditingEntityListener { //Listener is listening all actions - save, update, delete ... - and gives back information
//We are creating listening mechanism. It is listening baseEntity;
//We created different class because we need to extend the class.

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity){ // listening baseEntity
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setInsertDateTime(LocalDateTime.now()); //when anything happens set the time on "now"
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication !=null & !authentication.getName().equals("anonymousUser")); //anonymousUser means valid user here, not anonymousUser.
        Object principal = authentication.getPrincipal();
        baseEntity.setInsertUserId(((UserPrincipal )principal).getId()); //we need to set User ID. converter is in UserPrincipal class.
        baseEntity.setLastUpdateUserId(((UserPrincipal )principal).getId());

    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication !=null & !authentication.getName().equals("anonymousUser")); //anonymousUser means valid user here, not anonymousUser.
        Object principal = authentication.getPrincipal();
        baseEntity.setLastUpdateUserId(((UserPrincipal )principal).getId());

    }

    }

    /*
    In the baseEntity whenever we update/create something we keep information who did it.
    And by adding BaseEntity Listener I am assigning those values to db. Security will give us ID, it is not hardcoded anymore.
     */
