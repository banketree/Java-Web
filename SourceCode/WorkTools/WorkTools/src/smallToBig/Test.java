package smallToBig;

public class Test {
	public static void main(String[] args) {
		String str ="abcd";
		char[] arr = str.toCharArray();   //分割字符串为char数组
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
		System.out.println(String.valueOf(arr));   //合并char数组为字符串
	}
}
