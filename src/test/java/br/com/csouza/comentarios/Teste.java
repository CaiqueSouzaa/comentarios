package br.com.csouza.comentarios;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste {
    @Test
    public void teste() {
        final String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent aliquet justo libero, non ullamcorper turpis venenatis at. Suspendisse rutrum congue libero, eget venenatis nisl consectetur ut. Quisque rutrum quam sit amet elit pellentesque, non feugiat magna accumsan. Aenean condimentum imperdiet tristique. Sed eu ipsum viverra, placerat sapien vel, tincidunt quam. Mauris quis vestibulum nisi. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Praesent facilisis, nisl vitae ultrices pharetra, justo nisl tincidunt sem, et aliquam est quam in tortor. Integer sed massa luctus, auctor eros vel, euismod ipsum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus mollis eros vitae nunc semper congue. Mauris convallis tempus purus, ut dignissim mauris. Nulla sed libero vel leo venenatis sodales nec non erat. ";
        final String[] stringArray = text.split(" ");
        final List<String> newTextList = new ArrayList<>();
        int stringLength = 6;

        final List<List<String>> lines = new ArrayList<>();
        final List<String> line = new ArrayList<>();
//        for (String item : stringArray) {
//            if (line.size() < stringLength) {
//                line.add(item);
//            } else {
//                final StringBuilder lineText = new StringBuilder();
//                line.forEach(i -> lineText.append(" ").append(i));
//
//                newTextList.add(lineText.toString().trim());
//                line.clear();
//            }
//        }


        System.out.println(newTextList);
    }
}
