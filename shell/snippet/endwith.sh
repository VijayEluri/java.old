#!/bin/bash

# check where str is endwith suffix
# $1 the str to be checked
# $2 the suffix to be end with
# return 0: not end with; 1: end with suffix
function endwith()
{
    local str=$1
    local suffix=$2
    local strlen=${#str}
    local suffixlen=${#suffix}
    local ret="0"
    if [ $strlen -ge $suffixlen ]; then
        local tmp=${str:$strlen-$suffixlen}
        if [ "$tmp" == "$suffix" ]; then
            ret="1"
        fi
    fi
    
    echo $ret
}

str="12345"
suffix="45"

echo "usage 1, use command"
ret=`endwith $str $suffix`
if [ $ret -eq 1 ]; then
    echo "$str is end with $suffix"
else
    echo "$str is not end with $suffix"
fi

echo
echo "usage 2, use as command for short"
if [ `endwith $str $suffix` -eq 1 ]; then
    echo "$str is end with $suffix"
else
    echo "$str is not end with $suffix"
fi


