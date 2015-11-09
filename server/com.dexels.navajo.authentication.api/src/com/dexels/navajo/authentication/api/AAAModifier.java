package com.dexels.navajo.authentication.api;

import java.util.Date;


public interface AAAModifier {
    public void updatePassword(String tenant, String username, String newPassword, Date dateStart, Date dateEnd);
    
}