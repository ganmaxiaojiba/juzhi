package com.juzhi.controller;

import com.juzhi.entity.Category;
import com.juzhi.entity.Info;
import com.juzhi.wrapper.InfoCategoryListWrapper;
import com.juzhi.wrapper.InfoDetailedWrapper;
import com.juzhi.wrapper.InfoListWrapper;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjwan on 5/11/14.
 */
@Controller
@RequestMapping("/info")
public class InfoController {
    @RequestMapping(value = "/categorylist", method = RequestMethod.GET)
    public ModelAndView infoCategoryList(Model model) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost("http://www.witsun.cn/app/infoCategoryList.php");
            //List<NameValuePair> list = new ArrayList<>(1);
            // list.add(new BasicNameValuePair("infoid", "11245"));
//            httpPost.setEntity(new UrlEncodedFormEntity(list));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();

            InfoCategoryListWrapper wrapper = mapper.readValue(String.valueOf(result), InfoCategoryListWrapper.class);
            List<Category> categories = wrapper.getCategorylist();
            model.addAttribute("categories", categories);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("success");
    }


    @RequestMapping(value = "/infolist", method = RequestMethod.GET)
    public ModelAndView infoList(Model model) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost("http://www.witsun.cn/app/infoList.php");
            List<NameValuePair> list = new ArrayList<>(3);
            list.add(new BasicNameValuePair("categoryId", "12"));
            list.add(new BasicNameValuePair("pageSize", "10"));
            list.add(new BasicNameValuePair("pageNum", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(list));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();

            InfoListWrapper wrapper = mapper.readValue(String.valueOf(result), InfoListWrapper.class);
            List<Info> infos = wrapper.getInfoList();
            model.addAttribute("infos", infos);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("success");
    }


    @RequestMapping(value = "/detailed", method = RequestMethod.GET)
    public ModelAndView infoDetailed(Model model) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost("http://www.witsun.cn/app/infoDetailed.php");
            List<NameValuePair> list = new ArrayList<>(1);
            list.add(new BasicNameValuePair("infoid", "11245"));
            httpPost.setEntity(new UrlEncodedFormEntity(list));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();

            InfoDetailedWrapper wrapper = mapper.readValue(String.valueOf(result), InfoDetailedWrapper.class);

            model.addAttribute("infoDetailed", wrapper.getInfoDetailed());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("success");
    }

}
