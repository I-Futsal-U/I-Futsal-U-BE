package com.ifutsalu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifutsalu.domain.stadium.ParkingLot;
import com.ifutsalu.domain.stadium.Stadium;
import com.ifutsalu.domain.stadium.StadiumRepository;
import com.ifutsalu.dto.response.gonggong.OuterRespDto;
import com.ifutsalu.dto.response.gonggong.StadiumInfoDto;
import com.ifutsalu.dto.response.gonggong.StadiumJsonDto;
import com.ifutsalu.util.WebClientUtil;
import java.io.FileReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final StadiumRepository stadiumRepository;
    @Value("${gonggong.key}")
    private String key; // static으로 선언하는 순간 가져올 수 없게 됨.
    private final String URL = "http://openAPI.seoul.go.kr:8088/";
    WebClientUtil webClientUtil;

    /**
     * for initialize Stadium Entity Data. Only load once.
     * @throws IOException FileReader, ObjectMapper.readValue..
     */
    public void initStadiumData() throws IOException {
        FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/src/main/java/com/ifutsalu/util/stadium.json");

        ObjectMapper objectMapper = new ObjectMapper();
        StadiumJsonDto stadiumJsonDto = objectMapper.readValue(fileReader, StadiumJsonDto.class);

        for (StadiumInfoDto dto : stadiumJsonDto.getData()) {
            Stadium stadium = Stadium.builder()
                    .address(dto.getAddressName())
                    .name(dto.getPlaceName())
                    .size("123")
                    .stadiumImageUrl("test")
                    .showerRoom(Boolean.TRUE)
                    .shoesRental(Boolean.TRUE)
                    .parkingLot(ParkingLot.FREE)
                    .notification("설명입니다.")
                    .build();
            stadiumRepository.save(stadium);
        }
    }

    //    @Scheduled(cron = "0 0 12 15 * ?")
    @Scheduled(fixedDelay = 1000000)
    public void scheduleFixedDelayTask() {
        OuterRespDto result =
                webClientUtil.sendRequest(URL + key + "/json/ListPublicReservationSport/1/10/풋살장");

        // 저장한 Stadium 정보에 매핑해서 넣기..
    }


}
