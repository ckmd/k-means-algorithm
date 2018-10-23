/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmeans;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author rachmad
 */
public class Kmeans {

    static FileInputStream filestream;
    static DataInputStream in;
    static BufferedReader br;
    static String[] label;
    static Scanner scan;
    static ArrayList<String[]> data;
    static double[][] node;
    static double[][] jarak;
    static double[][] centroid;
    static double[][] centroidBaru;
    static double[] klus;
    static double[] sumx;
    static double[] sumy;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        //input all data
        filestream = new FileInputStream("dataset.txt");
        in = new DataInputStream(filestream);
        br = new BufferedReader(new InputStreamReader(in));
        data = new ArrayList<>();

        String strLine;
        int i = 1;

        while ((strLine = br.readLine()) != null) {//memecah label, attribut, dan data
            if (i == 1) {
                label = strLine.split(",");
            } else {
                data.add(strLine.split(","));//data get didapat per baris
            }
            i++;
        }
        jarak = new double[data.size() * 2][data.size()];
        node = new double[data.size()][20];
        centroid = new double[100][100];
        centroidBaru = new double[100][100];
        klus = new double[10];
        sumx = new double[10];
        sumy = new double[10];
        for (int a = 0; a < data.size(); a++) {
            node[a][0] = Integer.parseInt(data.get(a)[0]);
            node[a][1] = Integer.parseInt(data.get(a)[1]);
            node[a][2] = Integer.parseInt(data.get(a)[2]);
//            System.out.println(node[a][1] + "," + node[a][2]);
        }
        //input nilai k
        int k = 2;
        //membangkitkan centroid secara random
        Random rand = new Random();
        for (int cent = 0; cent < k; cent++) {
            int x = rand.nextInt(100) + 1;
            int y = rand.nextInt(100) + 1;
            centroid[cent][0] = cent;
            centroid[cent][1] = x;
            centroid[cent][2] = y;
        }
        for (int cent = 0; cent < k; cent++) {
            System.out.println("centroid "+cent+" awal : "+centroid[cent][1] + ", " + centroid[cent][2]);
        }
        // menghitung jarak setiap data dengan centroid
//        int n = 0;
centroidBaru[0][1] = 0;
centroidBaru[0][2] = 0;
        do {
            if (centroidBaru[0][1] != 0 && centroidBaru[0][2] != 0) {
                centroid[0][1] = centroidBaru[0][1];
                centroid[0][2] = centroidBaru[0][2];
                centroid[1][1] = centroidBaru[1][1];
                centroid[1][2] = centroidBaru[1][2];
            }
            for (int d = 0; d < data.size(); d++) {
                for (int c = 0; c < k; c++) {
                    jarak[d][c] = Math.sqrt(Math.pow((double) (centroid[c][1] - node[d][1]), 2) + Math.pow((double) (centroid[c][2] - node[d][2]), 2));
                }
            }
            for (int c = 0; c < data.size(); c++) {
                for (int d = 0; d < k; d++) {
//                System.out.println("jarak node " + c + " dengan centroid " + d + " adalah : " + jarak[c][d]);
                }
            }
            for(int b = 0; b<k;b++){
                klus[b] = 0;
            }
            // mengurutkan data terdekat pada setiap centroid
            for (int b = 0; b < data.size(); b++) {
                if (jarak[b][0] < jarak[b][1]) {
//                    System.out.println(jarak[b][0] + " centroid " + 0);
                    node[b][3] = 0;
                    klus[0]++;
                } else {
//                    System.out.println(jarak[b][1] + " centroid " + 1);
                    node[b][3] = 1;
                    klus[1]++;
                }
            }
            System.out.println(klus[0] + "," + klus[1]);
            for (int a = 0; a < k; a++) {
                sumx[a] = 0;
                sumy[a] = 0;
            }
            // menghitung nilai centroid baru dari rata2 lokasi data setiap centroid
            for (int a = 0; a < data.size(); a++) {
                if (node[a][3] == 0) {
                    sumx[0] += node[a][1];
                    sumy[0] += node[a][2];
                } else if (node[a][3] == 1) {
                    sumx[1] += node[a][1];
                    sumy[1] += node[a][2];
                }
            }
            System.out.println("sum1" + sumx[1] + "," + klus[1]);
            for (int a = 0; a < k; a++) {
                centroidBaru[a][0] = a;
                centroidBaru[a][1] = sumx[a] / klus[a];
                centroidBaru[a][2] = sumy[a] / klus[a];
            }
            System.out.println("centroid 0 baru : " + centroidBaru[0][1] + ", " + centroidBaru[0][2]);
            System.out.println("centroid 1 baru : " + centroidBaru[1][1] + ", " + centroidBaru[1][2]);
        } // pengecekan do while jika centroid baru berbeda
        while (centroid[0][1] != centroidBaru[0][1] && centroid[1][2] != centroidBaru[1][2]);
        //menampilkan data setiap kluster
        for(int it= 0; it < k; it++){
        System.out.println("node "+it);
        for(int a = 0; a < data.size() ; a++){
            if(node[a][3]==it)
                System.out.print(node[a][0]+",");
        }
        System.out.println("");            
        }
//        System.out.println("node b");
//        for(int a = 0; a < data.size() ; a++){
//            if(node[a][3]==1)
//                System.out.print(node[a][0]+",");
//        }
    }

}
