public class Solution {
    public String addBinary(String a, String b) {
        char[] charA = a.toCharArray();
        char[] charB = b.toCharArray();
        char[] res = new char[charA.length > charB.length ? charA.length + 1 : charB.length + 1];
        char flag = '0';
        int k = res.length - 1, i = charA.length - 1, j = charB.length - 1, temp;
        while (k >= 0) {
            if (i >= 0 && j >= 0)
                temp = charA[i--] + charB[j--] + flag;
            else if (i >= 0 && j < 0)
                temp = charA[i--] + '0' + flag;
            else if (i < 0 && j >= 0)
                temp = '0' + charB[j--] + flag;
            else
                temp = '0' + '0' + flag;

            switch (temp) {
                case 144 : res[k--] = '0';flag = '0';break;
                case 145 : res[k--] = '1';flag = '0';break;
                case 146 : res[k--] = '0';flag = '1';break;
                case 147 : res[k--] = '1';flag = '1';break;
            }
        }
        return res[0] == '0' ? new String(res, 1, res.length - 1) : new String(res);
    }
}