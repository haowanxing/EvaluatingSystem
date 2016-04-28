package dao;

import java.io.File;
import java.io.IOException;

public class CreateFileUtil {

public static boolean CreateFile(String destFileName) {
    File file = new File(destFileName);
    if (file.exists()) {
     System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
     return false;
    }
    if (destFileName.endsWith(File.separator)) {
     System.out.println("创建单个文件" + destFileName + "失败，目标不能是目录！");
     return false;
    }
    if (!file.getParentFile().exists()) {
     System.out.println("目标文件所在路径不存在，准备创建。。。");
     if (!file.getParentFile().mkdirs()) {
      System.out.println("创建目录文件所在的目录失败！");
      return false;
     }
    }

    // 创建目标文件
    try {
     if (file.createNewFile()) {
      System.out.println("创建单个文件" + destFileName + "成功！");
      return true;
     } else {
      System.out.println("创建单个文件" + destFileName + "失败！");
      return false;
     }
    } catch (IOException e) {
     e.printStackTrace();
     System.out.println("创建单个文件" + destFileName + "失败！");
     return false;
    }
}



public static boolean createDir(String destDirName) {
    File dir = new File(destDirName);
    if(dir.exists()) {
     System.out.println("创建目录" + destDirName + "失败，目标目录已存在！");
     return false;
    }
    if(!destDirName.endsWith(File.separator))
     destDirName = destDirName + File.separator;
    // 创建单个目录
    if(dir.mkdirs()) {
     System.out.println("创建目录" + destDirName + "成功！");
     return true;
    } else {
     System.out.println("创建目录" + destDirName + "成功！");
     return false;
    }
}



public static String createTempFile(String prefix, String suffix, String dirName) {
    File tempFile = null;
    try{
    if(dirName == null) {
     // 在默认文件夹下创建临时文件
     tempFile = File.createTempFile(prefix, suffix);
     return tempFile.getCanonicalPath();
    }
    else {
     File dir = new File(dirName);
     // 如果临时文件所在目录不存在，首先创建
     if(!dir.exists()) {
      if(!CreateFileUtil.createDir(dirName)){
       System.out.println("创建临时文件失败，不能创建临时文件所在目录！");
       return null;
      }
     }
     tempFile = File.createTempFile(prefix, suffix, dir);
     return tempFile.getCanonicalPath();
    }
    } catch(IOException e) {
     e.printStackTrace();
     System.out.println("创建临时文件失败" + e.getMessage());
     return null;
    }
}

/**
 * 删除单个文件
 * @param   sPath    被删除文件的文件名
 * @return 单个文件删除成功返回true，否则返回false
 */
public static boolean deleteFile(String sPath) {
    boolean flag = false;
    File file = new File(sPath);
    // 路径为文件且不为空则进行删除
    if (file.isFile() && file.exists()) {
        file.delete();
        flag = true;
    }
    return flag;
}

public static void main(String[] args) {
    // 创建目录
    

  
}




}
