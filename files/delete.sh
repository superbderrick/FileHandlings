!/bin/bash

set -e

TARGET_FOLDER="Your folder"

echo "=== Delete files === "

if [ -d "$TARGET_FOLDER" ] ; then
    echo "=== There are files === "
    rm -rf "$TARGET_FOLDER"/*
    echo "=== Done === "
else 
    echo "There are no files."
    echo "exit"
fi
 

echo "Done delete process"