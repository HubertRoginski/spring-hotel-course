package org.springproject.springproject.service;


import org.springproject.springproject.model.ReservationsManagement;

import java.util.List;

public interface ReservationsManagementService {

    ReservationsManagement getCurrentReservationsManagement(Integer page, Integer size);
    ReservationsManagement getFutureReservationsManagement(Integer page, Integer size);
    ReservationsManagement getOldReservationsManagement(Integer page, Integer size);
}
