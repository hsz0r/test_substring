/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_substring;

import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hsz0r
 */
public class substring {

    private final int R = 256;
    private final int PRIME = 27644437;

    public ArrayList<Integer> Boyer_Moore(String text, String pattern) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int bmBc[] = new int[R];
        int bmGs[] = new int[R];

        preBmBc(pattern, pattern.length(), bmBc);
        preBmGs(pattern, pattern.length(), bmGs);

        int j = 0;

        while (j <= text.length() - pattern.length()) {

            int i;
            for (i = pattern.length() - 1; i >= 0 && text.charAt(i + j)
                    == pattern.charAt(i); i--) ;

            if (i < 0) {

                result.add(j);
                j += bmGs[0];

            } else {
                j += bmGs[i] > bmBc[text.charAt(i + j)] - pattern.length() + 1
                        + i
                                ? bmGs[i] : bmBc[text.charAt(i + j)]
                                - pattern.length() + 1 + i;
            }
        }
        return result;
    }

    private void preBmBc(String target, int targetLength, int[] bmBc) {

        for (int i = 0; i < R; i++) {
            bmBc[i] = targetLength;
        }

        for (int i = 0; i < targetLength - 1; i++) {
            bmBc[target.charAt(i)] = targetLength - i - 1;
        }
    }

    private void suffixes(String target, int targetLength, int[] suff) {

        int f = 0;
        int g = targetLength - 1;

        for (int i = targetLength - 2; i >= 0; i--) {
            if (i > g && suff[i + targetLength - 1 - f] < i - g) {
                suff[i] = suff[i + targetLength - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && target.charAt(g)
                        == target.charAt(g + targetLength - 1 - f)) {
                    --g;
                }
                suff[i] = f - g;
            }
        }
        suff[targetLength - 1] = targetLength;

    }

    private void preBmGs(String target, int targetLength, int[] bmGs) {

        int i, j = 0;

        int[] suff = new int[targetLength];

        suffixes(target, targetLength, suff);

        for (i = 0; i < targetLength; ++i) {
            bmGs[i] = targetLength;
        }

        for (i = targetLength - 1; i >= 0; --i) {
            if (suff[i] == i + 1) {
                for (; j < targetLength - 1 - i; ++j) {
                    if (bmGs[j] == targetLength) {
                        bmGs[j] = targetLength - 1 - i;
                    }
                }
            }

        }

        for (i = 0; i <= targetLength - 2; ++i) {
            bmGs[targetLength - 1 - suff[i]] = targetLength - 1 - i;
        }
    }

    public ArrayList<Integer> Rabin_Karp(String text, String pattern) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        int M = pattern.length();
        int N = text.length();
        int i, j;
        int p = 0;
        int t = 0;
        int h = 1;

        for (i = 0;
                i < M - 1; i++) {
            h = (h * R) % PRIME;
        }

        for (i = 0;
                i < M;
                i++) {
            p = (R * p + pattern.charAt(i)) % PRIME;
            t = (R * t + text.charAt(i)) % PRIME;
        }

        for (i = 0;
                i <= N - M;
                i++) {

            if (p == t) {
                for (j = 0; j < M; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                if (j == M) {
                    result.add(i);
                }
            }

            if (i < N - M) {
                t = (R * (t - text.charAt(i) * h) + text.charAt(i + M)) % PRIME;
                if (t < 0) {
                    t = (t + PRIME);
                }
            }
        }
        return result;

    }

    public ArrayList<Integer> Knuth_Morris_Pratt(String text, String pattern) {
        ArrayList<Integer> result = new ArrayList<>();

        int[] prefix = prefixfunc(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = prefix[j];
            }
            if (j == pattern.length()) {
                result.add(i - pattern.length());
                j = prefix[j];
            }
        }

        return result;
    }

    private int[] prefixfunc(String pattern) {
        int[] prefix = new int[pattern.length() + 1];

        int j = 1;
        int k = 0;

        prefix[0] = -1;

        while (j < pattern.length()) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                prefix[j] = k;
            } else {
                k = prefix[k];
            }
        }
        return prefix;
    }
}
