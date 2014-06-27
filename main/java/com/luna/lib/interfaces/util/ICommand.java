package com.luna.lib.interfaces.util;

import com.luna.lib.exception.CommandException;

public interface ICommand {
    public void onCommand(String[] args) throws CommandException;
    
    public String getName();
    
    public String getSyntax();
}
