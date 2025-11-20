package com.lti.flipfit.dao;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GymCenterFlipFitDao {

    public final Map<String, GymCenter> centerStore = new HashMap<>();
    public final Map<String, List<Slot>> slotStore = new HashMap<>();
}
