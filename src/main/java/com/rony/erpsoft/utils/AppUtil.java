/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.utils;

import com.rony.erpsoft.user_auth.service.AppSettingService;
import com.rony.erpsoft.user_auth.service.SessionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author sarker
 */
@PropertySource(value = "classpath:application.properties")
@Component
public class AppUtil {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${app.full_name}")
    private String APP_FULL_NAME;
    
    @Value("${app.rootdir}")
    private String ROOTDIR;
    
    @Value("${scheduler.endpoint}")
    private String SCHEDULER_ENDPOINT;

    @Value("${app.name.hash}")
    private String SECRET_KEY;
    
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    AppSettingService appSettingService;
    
    public String getDefaultPasskey() {
        try {
            return toString(appSettingService.getAppSettings().get("default_pkey"));
        } catch (Exception e) {
        }
        return null;
    }
    
    
    public synchronized long getMaxVersion(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public String SHA512(String strToHash) {
        String generatedPaswrd = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(getSalt());
            byte[] bytes = md.digest(strToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPaswrd = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return generatedPaswrd;
    }

    public String SHA256(String strToHash) {
        String generatedPaswrd = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(getSalt());
            byte[] bytes = md.digest(strToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPaswrd = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return generatedPaswrd;
    }
    
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        String saltVal = "1m8P3m@1lS3nd37";
        return saltVal.getBytes(StandardCharsets.UTF_8);
    }
    
    public synchronized String retrievePaswd(String hasStr) {
        String plainStr = "";
        String decNewPassword =  new String(Base64.getDecoder().decode(hasStr));
        AES aesUtil = new AES(128, 1000);
        if (decNewPassword != null && decNewPassword.split("::").length == 3) {
            plainStr = aesUtil.decrypt(decNewPassword.split("::")[1], decNewPassword.split("::")[0], KEY.AES_SECRET, decNewPassword.split("::")[2]);
        }
        return plainStr;
    }

    public synchronized static UUID toUUID(Object obj) {
        try {
            return UUID.fromString(obj.toString());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public synchronized static UUID toUUID(String obj) {
        try {
            return UUID.fromString(obj);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public static String toString(Object str) {
        if (str == null) {
            return "";
        }
        try {
            return String.valueOf(str);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return "";
    }

    public static int toInt(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }
    
    public static int toInt(Object number) {
        try {
            return Integer.parseInt(number.toString().trim());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(Object number) {
        try {
            return Long.parseLong(number.toString().trim());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static long toLong(String number) {
        try {
            return Long.parseLong(number.trim());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0;
    }

    public static boolean toBoolean(Object val) {
        try {
            return (boolean) val;
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return false;
    }

    public static double toDouble(Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return 0.0f;
    }

    public static Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {

            return sdf.parse(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    public static Date toDateTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return getDateTime();
    }

    public static Date getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            return sdf.parse(sdf.format(new Date()));
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    /*public static boolean isValidEmail(String email){
        Pattern ep = Pattern.compile("[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]+");
        return ep.matcher(email).matches();
    }*/
    
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean isValidLanId(String lanId) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]$";
        return lanId.matches(regex);
    }
    
    public static Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(new Date()).toString());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return new Date();
    }
    
    public static String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(new Date());
        } catch (Exception ex) {
            //ex.printStackTrace();
        }

        return null;
    }
    
    public static boolean isStrongPassword(String _str) throws Exception{
        boolean isUpr = false, isLwr = false, isNmr = false, isSpl = false;
        String upr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lwr = "abcdefghijklmnopqrstuvwxyz";
        String nmr = "0123456789";
        String spl = "!@#$%^&()_<>[]{}.+";

        if( _str.length() < 8 ) throw  new Exception("Password length at least 8 characters");
        for( int i=0 ; i<_str.length() ; i++ ){
            if( upr.indexOf(_str.charAt(i)) > -1 ) isUpr = true;
            if( lwr.indexOf(_str.charAt(i)) > -1 ) isLwr = true;
            if( nmr.indexOf(_str.charAt(i)) > -1 ) isNmr = true;
            if( spl.indexOf(_str.charAt(i)) > -1 ) isSpl = true;
        }

        if( !isUpr ) throw  new Exception("Password should contain at least one (A-Z) characters");
        if( !isLwr ) throw  new Exception("Password should contain at least one (a-z) characters");
        if( !isNmr ) throw  new Exception("Password should contain at least one (0-9) characters");
        if( !isSpl ) throw  new Exception("Password should contain at least one (!@#$%^&()_<>[]{}.+) characters");

        return true;
    }
    
    public int getSessionTimeout() {
        try {
            return toInt(appSettingService.getAppSettings().get("sess_timeout"))*60; //convert minute to seconds
        } catch (Exception e) { }
        return 300;
    }
    
    public String genToken(){
        String tmpToken = SHA512("SaRkEr" + UUID.randomUUID());
        sessionService.setToken(tmpToken);
        return tmpToken;
    }
    
    public String getToken(){
        return sessionService.getToken();
    }

    public String getAppName() {
        try {
            return APP_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CMS";
    }

    public String getAppFullName() {
        try {
            return APP_FULL_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Contact Management System";
    }
    
    public String getRootDir() {
        try {
            return ROOTDIR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/AIPIMG";
    }

    public String getSecretKey() {
        try {
            return SECRET_KEY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String getSchedulerEndpoint() {
        try {
            return SCHEDULER_ENDPOINT + "/endpoint";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public Map<String, Object> processString(String str){
        Map<String, Object> resp = new HashMap<>();
        
        try{
            JSONObject jSONObject = new JSONObject(str);
            
            try{
                resp.put("baseUnit", jSONObject.getString("baseUnit"));
            } catch(Exception ex){}
            
            resp.put("actualVal", jSONObject.getJSONArray("measurements").getJSONObject(0).get("value") );
            
        } catch(Exception e){}
        
        return resp;
    }
    
    public String getSchedulerInfoEndpoint() {
        try {
            return SCHEDULER_ENDPOINT + "/hterminal";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public static String getHeaderInfo(HttpServletRequest request, String keyName) {
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if( key.equals(keyName) ){
                return request.getHeader(key);
            }
        }
        return "";
    }

    public static String rightPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

    public static String leftPad(String str, Integer length, char car) {
        return (str + String.format("%" + length + "s", "").replace(" ", String.valueOf(car))).substring(0, length);
    }

    public static int getYear(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentSystemYear() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * Get previous date of the given date
     * @param date
     * @return date
     */
    public static Date getPreviousDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get next date of the given date
     * @param date
     * @return date
     */
    public static Date getNextDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * Get first date of the given date's month
     * @param date
     * @return date
     */
    public static Date getStartDateOfMonth(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getDateFormatAsString(Date pDate) {
        String stringDate = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (pDate != null) {
            stringDate = dateFormat.format(pDate);
        }
        return stringDate;
    }

    public static String getDateStringByPattern(Date pDate, String pattern) {
        String stringDate = "";
        if(pattern.equals("")){
            pattern="dd-MM-yyyy";
        }

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        if (pDate != null) {
            stringDate = dateFormat.format(pDate);
        }
        return stringDate;
    }

    public static Date getLocalDateTimeToDate(LocalDateTime localDateTime) {

        try {
            String formatDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatDateTime);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getStringBetweenTwoCharacters(String value){

        value = value.substring(value.indexOf("[") + 1);
        value = value.substring(0, value.indexOf("]"));

        return value;
    }

}
