#!/bin/bash

function verifyOutput() {
  allname="$(basename $1)"
  filename="${allname%.*}"
  fileext="${allname##*.}"
 
  diff -arqN $1 $2 > /dev/null
  if [[ $? -ne 0 ]]; then
  	echo $filename": "$filename"."$fileext": Error: Files are different."
  fi
}

act_path=`pwd`
in_path=$act_path/in
ref_path=$act_path/ref
tmp_path=$act_path/tmp
dist_path=$act_path/../dist

# build
echo '== Building ... '
ant -f $act_path/.. -Dnb.internal.action.name=rebuild clean jar -q
if [[ $? -ne 0 ]]; then
  echo "Error: Build failed."
  exit 1
fi 
echo '== Running ... (if no error is shown then test is passed)'
rm -f -r $tmp_path 2> /dev/null
mkdir $tmp_path 2> /dev/null
cd $in_path
find -L *.config | while read line; do
  filename="${line%.*}"
  line=`readlink -e $line`
  java -jar $dist_path/XmlTreeEditor.jar $line 2> $tmp_path"/"$filename".out"
	# testing output
  verifyOutput $tmp_path"/"$filename".out" $ref_path"/"$filename".out" 
  # testing result xml
  if [ -f $ref_path"/"$filename".xml2" ]; then 
    if [ -f $in_path"/"$filename".xml2" ]; then
      verifyOutput $in_path"/"$filename".xml2" $ref_path"/"$filename".xml2"
      mv $in_path"/"$filename".xml2" $tmp_path"/"$filename".xml2" 
    else 
      echo $filename": Error: Expected xml file as result of the program."
    fi
  else
    if [ -f $in_path"/"$filename".xml2" ]; then	
       echo $filename": Error: Unexpected xml file as result of the program."
       mv $in_path"/"$filename".xml2" $tmp_path"/"$filename".xml2" 
    fi
  fi
done

