!/bin/bash

set -e

TARGET_FOLDER="your target folder"
DESTNATION_FOLDER="your destnation folder"


if [ -d "$DESTNATION_FOLDER" ] ; then

for mFile in ${DESTNATION_FOLDER}/*.m # "file type example m fle "
do
    cp $mFile $TARGET_FOLDER

done


    
    
else 
    echo "There are no files."
    echo "exit"
fi
 

echo "Done copy process"