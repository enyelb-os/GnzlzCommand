package tools.gnzlz.command.process;

import tools.gnzlz.command.process.functional.FunctionInputProcess;
import tools.gnzlz.command.process.functional.FunctionOutputProcess;

import java.util.Scanner;

public class SystemIO {

    public static FunctionOutputProcess OUT = System.out::print;

    public static FunctionInputProcess INP = new Scanner(System.in)::nextLine;
}
