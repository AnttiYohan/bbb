package com.example.braceletblazeboss;

public class ApiResult
{
    protected String json = "";

    public ApiResult()
    {
        this.json = "";
    }

    public ApiResult(String json)
    {
        this.json = json;
    }

    public void set(String json)
    {
        this.json = json;
    }

    public String get()
    {
        return this.json;
    }
}
