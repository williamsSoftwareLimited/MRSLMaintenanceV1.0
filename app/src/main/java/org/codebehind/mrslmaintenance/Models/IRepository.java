package org.codebehind.mrslmaintenance.Models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 29/12/2014.
 */
public interface IRepository<t> {
    ArrayList<t> getList();
    t getItem(UUID id);
    t add(t item);
    t delete(t item);
    t update(t item);
    t getTemplate(t item);
}
