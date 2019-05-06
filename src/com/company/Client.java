package com.company;
import java.util.*;

import java.io.*;

public class Client{
    private String flightName = null;
    private int row=0;
    private int rowlength=0;
    private Flight flight = null;
    private String cmdString = null;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) {
          new  Client().CommandShell();
    }

    private void CommandShell(){
        while(true){
            ReadCommand();
            ProcessCommand();
        }
    }
    private void ReadCommand(){
        if(flightName==null){
            System.out.println("Please Create The Flight Data First！");
            System.out.println("输入格式: create flightname rows rowlenght");
            System.out.println("请输入：");

        }
        try{
            cmdString=br.readLine().trim();
        }catch (IOException e){
            System.out.println("输入格式错误，请重新输入！");
            cmdString=null;
        }
    }
    private void ProcessCommand(){
        String[] cmds;
        String c;
        if(cmdString!=null){
            cmds=Command(cmdString);
            if(cmds!=null) {
                c=cmds[0].toLowerCase();
                if (c.equals("create")) {

                    if (flightName == null)
                        createCommand(cmds);
                    else System.out.println("创建错误!");

                } else if (c.equals("reserve")) {

                    if (flightName != null)
                        reserveCommand(cmds);

                } else if (c.equals("cancel")) {

                    if (flightName != null)
                        cancelCommand(cmds);

                } else if (c.equals("list")) {

                    if (flightName != null)
                        listCommand(cmds);

                } else if (c.equals("exit")) {

                    System.exit(0);

                } else {

                    System.out.println(" 命令错误！ ");
                    cmdString = null;
                }
            }
        }
    }
    private String[] Command(String cmdstr){
        int c=0;
        String[] cmd;
        StringTokenizer st=new StringTokenizer(cmdstr);
        if((c=st.countTokens())==0) {
            return null;
        }
        cmd=new String[c];
        for(int i=0;i<c;i++)
            cmd[i]=st.nextToken();
        return cmd;
    }

    private int sturni(String str){
        int val=0;
        try {
            val = Integer.parseInt(str);
        }catch (Exception e){
            val = Integer.MAX_VALUE;
        }
        return val;
    }
    private void createCommand(String[] cmds){
        if(cmds.length!=4){
            System.out.println("命令错误！");
        }
        else {
            flightName=cmds[1];
            row=sturni(cmds[2]);
            rowlength=sturni(cmds[3]);
            if(row <= 0|| rowlength <=0){
                System.out.println("输入座位不合法，请重新输入！");
                flightName=null;
                row=0;
                rowlength=0;
            }
            else {
                try {
                    flight = new Flight(flightName, row, rowlength);
                    System.out.println("创建成功！");
                }catch (Exception e){
                    System.out.println(e);
                    flightName=null;
                    row=0;
                    rowlength=0;
                }
            }

        }
    }
    private void reserveCommand(String[] cmds){
        if (cmds.length<=1){
            System.out.println("预定命令错误!");
        }
        String[] names =new String[cmds.length-1];

        for (int i=1;i<names.length;i++)
            names[i]=new String(cmds[i+1]);
        int[] bn=flight.reserve(names);
        if(bn[0]!=1){
            for(int i=0; i<bn.length;i++)
                System.out.println(names[i] + "预定的座位是:" + bn[i]);

        }
        else System.out.println("当前没有座位！");
    }
    private void cancelCommand(String[] cmds) {

        if (cmds.length != 2) {
            System.out.println("\n取消命令错误！");
        }
        int bookingNumber = sturni(cmds[1]);
        if (bookingNumber <= 0) {
            System.out.println("\n座位不存在!");
        }
        boolean state = flight.cancel(bookingNumber);
        if (state)
            System.out.println("您的座位已退订! ");
        else
            System.out.println("退订失败!");

    }
    private void listCommand(String[] cmds) {
        if (cmds.length != 1) {
            System.out.println("\n列表命令错误！");

        }
        Passenger[] passengerlist = flight.getPassengerList();
        int flag = 0;

        System.out.println("Name Booking Number Row Seat Position ");

        if (passengerlist == null || passengerlist.length <= 0)
            System.out.println("Now no seat is occupied!");

        else {


            for (int b = 0; b < passengerlist.length; b++) {
                flag = 0;
                if (passengerlist[b] != null) {
                    flag = 1;
                    System.out.println(formatStr(passengerlist[b].getName())
                                    + formatStr(""
                                    + passengerlist[b]
                                    .getBookingNumber())
                                    + formatStr("" + passengerlist[b].getRow())
                                    + formatStr(""
                                    + passengerlist[b]
                                    .getSeatPosition()));
                }
            }
            if (flag == 0)
                System.out.println("Now no seat is occupied!");

        }

    }
    private String formatStr(String s) {
        for (int i = 0; i < 16 - s.trim().length(); i++)
            s += ' ';
        return s;
    }
}

