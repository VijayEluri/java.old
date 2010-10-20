#!/bin/bash
# usage: run at lib
#   copy_eclipse_file.sh d:/eclipse/eclipse-SDK-3.6.1-win32/plugins  Eclipse  ../lib_src/Eclipse

function copyfile()
{
    echo "copy file $1 ..."
    jar_file=$1
    src_file=$2
    cp ${source_dir}/${jar_file}* ${jar_dir}
    cp ${source_dir}/${src_file}* ${src_dir}
}

source_dir=$1
jar_dir=$2
src_dir=$3
copyfile "org.eclipse.core.commands_"       "org.eclipse.core.commands.source_"
copyfile "org.eclipse.core.runtime_"        "org.eclipse.core.runtime.source_"
copyfile "org.eclipse.equinox.common_"      "org.eclipse.equinox.common.source_"
copyfile "org.eclipse.jface.text_"          "org.eclipse.jface.text.source_"
copyfile "org.eclipse.jface_"              "org.eclipse.jface.source_"
copyfile "org.eclipse.osgi_"                "org.eclipse.osgi.source_"
copyfile "org.eclipse.swt.win32.win32.x86_" "org.eclipse.swt.win32.win32.x86.source_"
copyfile "org.eclipse.text_"                "org.eclipse.text.source_"

