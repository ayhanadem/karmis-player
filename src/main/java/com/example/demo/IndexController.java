package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
public class IndexController {


    VideoInfo info;
    @PostMapping("/uploadFile")
    public  ModelAndView uploadFile(@RequestParam("file")MultipartFile file) throws IOException
    {

        ObjectMapper objectMapper = new ObjectMapper();
        info = objectMapper.readValue(file.getInputStream(),VideoInfo.class);
        return new ModelAndView("redirect:/");
    }
    @GetMapping("/")
    public ModelAndView index() {

        if(info== null)
        {
            createInfo();
        }


                return new ModelAndView("index")
                .addObject("videoId", info.getVideoId())
                .addObject("videoUrl", info.getVideoUrl())
                .addObject("videoType",info.getVideoType())
                .addObject("markers",info.getMarkers())
                ;
    }

    //markers?id=1928832
    @GetMapping("/markers")
    public List<Marker> markers(@RequestParam(required = false) Long id) {

        if(info== null)
        {
            createInfo();
        }
        return info.getMarkers();
    }

    public void  createInfo()
    {

        info = new VideoInfo();
        info.setVideoType("video/ogg");
        info.setVideoUrl("//vjs.zencdn.net/v/oceans.ogv");
        info.setVideoId(1);

        ArrayList<Marker> markers = new ArrayList<>();
        markers.add(new Marker(12, "",""));
        markers.add(new Marker(22, "","new"));
        markers.add(new Marker(33, "","old"));

        info.setMarkers(markers);

    }
}
