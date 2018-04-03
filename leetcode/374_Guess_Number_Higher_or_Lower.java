/* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
      int guess(int num); */

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        return tryGuess(1, n);
    }

    private int tryGuess(int low, int high) {
       /* An easy problem.There is no need to say much about it but the "mid".
        Considering the big integer,we should get it in this way.
        int mid = start+(end - start)/2;
        instead of
        int mid = (start+end)/2;*/

        int mid = low + (high - low) / 2;
        int temp = guess(mid);
        if (temp == 0)
            return mid;
        else if (temp == 1)
            return tryGuess(mid + 1, high);
        else
            return tryGuess(low, mid - 1);
    }
}