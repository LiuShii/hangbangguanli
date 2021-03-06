package com.company;

public class Flight implements FlightInterface {

    private String flightName;

    private int row;

    private int rowLength;

    private int[] fail = { -1 };

    private Passenger[] passengerList;

    public Flight(String FlightName, int rows, int rowLength)
    throws Exception{
        if(FlightName==null||rows==0||rowLength==0|| FlightName.trim().length() == 0)
            throw new Exception("航班错误");
        else {
            this.flightName=FlightName;
            this.row=rows;
            this.rowLength=rowLength;
            passengerList=new Passenger[row*rowLength];
            for (int i = 0; i < row * rowLength; i++)
                passengerList[i] = null;
        }
    }
    /**航班类的构造方法*/

    @Override
    public int[] reserve(String[] names) {         //预定座位
        if (names.length > rowLength)

            return fail;

        int i = 0, j = 0, k = 0;

        boolean flag = false;
        labelA: for (i = 0; i <= row - 1; i++) {

            for (j = 0; j <= rowLength - names.length; j++) {      // 从第i行第j列开始分配座位
                for (k = j; k <= j + names.length - 1; k++) {
                    if (passengerList[i * rowLength + k] != null)
                        break;
                }
                if (k > j + names.length - 1) {

                    flag = true;
                    break labelA;
                }
            }
        }

        if (!flag)
            return fail;

        int[] bn = new int[names.length]; 				//每一个旅客返回一个预订号

        for (k = j; k <= j + names.length - 1; k++) {
            bn[k - j] = i * rowLength + k + 1; 			//产生预订号，names[0]对应 bn[0]
            passengerList[i * rowLength + k] = new Passenger(names[k - j], i
                    * rowLength + k + 1, i, k);
        }
        return bn;
    }

    @Override
    public boolean cancel(int bookingNumber) {
        boolean Status = false;
        for (int i = 0; i < row * rowLength; i++) {
            if (passengerList[i] != null
                    && bookingNumber == passengerList[i].getBookingNumber()) {
                Status = true;
                passengerList[i] = null;
                break;
            }
        }
        return Status;
    }

    @Override
    public Passenger[] getPassengerList() {
        return passengerList;
    }



}