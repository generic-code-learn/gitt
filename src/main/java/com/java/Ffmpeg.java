package com.java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** dev修改
 * @description:
 * @author: raki
 * @create: 2020-12-31 11:07
 **/
public class Ffmpeg {

    // win程序路径
    private final String ffmpeg = "E:/Program/GreenSoft/youtube/ffmpeg.exe";

    /**
     * 文件路径处理
     *
     * @param parentPath
     * @return
     */
    public ArrayList<Map<String, String>> fileForCommand(String parentPath) {
        File parentFile = new File(parentPath);
        File[] files = parentFile.listFiles();
        ArrayList<Map<String, String>> commands = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName().substring(1);
            File[] targetDir = files[i].listFiles();
            HashMap<String, String> commandsMap = new HashMap<>();
            commandsMap.put("path", files[i].getPath());
            commandsMap.put("fileName", fileName);
            for (int j = 0; j < targetDir.length; j++) {
                String name = targetDir[j].getName();
                if (name.endsWith(".m4s")) {
                    if (name.startsWith("音频")) {
                        commandsMap.put("audio", name);
                    } else {
                        commandsMap.put("video", name);
                    }
                }
            }
            commands.add(commandsMap);
        }
        return commands;
    }

    /**
     * 音视频合并
     * @param fileForCommand
     * @return List<String>
     * @throws Exception
     */
    public List<String> format(ArrayList<Map<String, String>> fileForCommand) throws Exception {
        ArrayList<String> commandList = new ArrayList<>();
        for (int i = 0; i < fileForCommand.size(); i++) {
            Map fileMap = fileForCommand.get(i);
            String command = ffmpeg + " -i " + fileMap.get("path") + "/" + fileMap.get("video") + " -i " + fileMap.get("path") + "/" + fileMap.get("audio")
                    + " -c:v copy -strict experimental " + fileMap.get("path") + "/" + fileMap.get("fileName") + ".mp4";
            commandList.add(command);
        }
        return commandList;
    }
    // 音频提取

    /**
     * 执行命令
     * @throws Exception
     */
    public void run() throws Exception {
        ArrayList<Map<String, String>> fileForCommand = fileForCommand("E:\\123");
        List<String> commands = format(fileForCommand);
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < commands.size(); i++) {
            runtime.exec(commands.get(i));
            Thread.sleep(1000 * 3);
        }
    }
}
