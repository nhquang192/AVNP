package CTLs;

public class ConvertValue {
	public String byte2string(byte[] data, int len) {
		int i;
		StringBuffer strbuf = new StringBuffer();
		char[] buff = new char[len];

		for (i = 0; i < len; i++)
			buff[i] = (char) data[i];
		return strbuf.append(buff).toString();
	}
	
	public static int bytes2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00)
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	public static int bytes2int(byte[] res, int offset) {
		int targets = (res[offset] & 0xff) | ((res[offset+1] << 8) & 0xff00)
				| ((res[offset+2] << 24) >>> 8) | (res[offset+3] << 24);
		return targets;
	}
	public static byte[] int2bytes(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);
		targets[1] = (byte) ((res >> 8) & 0xff);
		targets[2] = (byte) ((res >> 16) & 0xff);
		targets[3] = (byte) ((res >>> 24));
		return targets;
	}
	/**  
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用 
     *   
     * @param src  
     *            byte数组  
     * @param offset  
     *            从数组的第offset位开始  
     * @return int数值  
     */    
 public static int bytesToInt(byte[] src, int offset) {  
     int value;    
     value = (int) ((src[offset] & 0xFF)   
             | ((src[offset+1] & 0xFF)<<8)   
             | ((src[offset+2] & 0xFF)<<16)   
             | ((src[offset+3] & 0xFF)<<24));  
     return value;  
}
}