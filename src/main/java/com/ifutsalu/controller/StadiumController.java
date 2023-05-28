package com.ifutsalu.controller;

import com.ifutsalu.domain.Stadium;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/stadium")
public class StadiumController {

    private List<Stadium> stadiums;

    public StadiumController() {
        stadiums = new ArrayList<>();
        stadiums.add(new Stadium("한강풋살파크", "서울 광진구 강변북로 139", "40x20m",
                true, false, Stadium.ParkingLot.PAID, "잔디 상태가 좋아요!"));

        stadiums.add(new Stadium("서울스타디움", "서울 마포구 성산동 515-39", "40x20m",
                false, true, Stadium.ParkingLot.NONE, "근처에 식당이 많아요."));
    }

    @GetMapping
    public ResponseEntity<?> getAllStadiums() {

        return ResponseEntity.ok(stadiums);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchStadiums(@RequestParam("keyword") String keyword) {

        return ResponseEntity.ok(stadiums);
    }
}