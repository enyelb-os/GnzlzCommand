module tools.gnzlz.command {
    requires transitive tools.gnzlz.system.io;

    exports tools.gnzlz.command;

    exports tools.gnzlz.command.command;
    exports tools.gnzlz.command.command.object;
    exports tools.gnzlz.command.command.functional;
    exports tools.gnzlz.command.command.data;

    exports tools.gnzlz.command.init;
    exports tools.gnzlz.command.init.functional;

    exports tools.gnzlz.command.process;

    exports tools.gnzlz.command.result;
    exports tools.gnzlz.command.result.functional;

    exports tools.gnzlz.command.group;
    exports tools.gnzlz.command.group.functional;
}