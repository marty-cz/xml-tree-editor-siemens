# xml-tree-editor-siemens

This project is written to prove my Java SE 7 skills for hiring management of [Siemens Corp](https://jobsearch.siemens.biz/career?career%5fns=job%5flisting&company=Siemens&navBarLevel=JOB%5fSEARCH&rcm%5fsite%5flocale=cs%5fCZ&career_job_req_id=173389&selected_lang=cs_CZ&jobAlertController_jobAlertId=&jobAlertController_jobAlertName=&_s.crb=Yg7P8r7oV1GsVgA9c5OzqlL54k0%3d).


## General Requirements

Xml tree editor is command line application that allows editing and validating xml files. Xml file and all required operations with it are specified in provided configuration. Validation will be done according to xml schema (The result of validation will be displayed in output (console), but will have no impact on the rest of the process).

Operation means some change in data or some structure change of provided xml tree.

### Output

The result output (the changed xml tree) will be stored to the same directory with same name as original file, only number `2` will be appended to the file extension. For example from `test.xml` it will create `test.xml2`.

Error messages are printed to the `stderr` (incl. message of "validation ok"). 
If error occurs (errors of validation input file is only printed) then application is quit with error code `1` otherwise `0` (OK).

## Usage

``` bash
java -jar XmlTreeEditor.jar <CONFIG_FILE_PATH>
```
where 
* `CONFIG_FILE_PATH` ... is path to a config file

### Config File

Config file is using simple XML structure.
``` xml
<?xml version="1.0" encoding="ISO-8859-2" ?>
<config>
  <input>test01.xml</input>
  <schema>schema01.xsd</schema>
  <operations>
    <operation operType="removeNode" nodeName="PRICE" />
    <operation operType="replaceValue" oldValue="UK" newValue="GB" />
  </operations>
</config>
```
where 
* `input` ... specifies the input xml file path, which is relative to path of config file. This node is MANDATORY.
* `schema` ... specifies the schema`s (XSD) file path of `input`, which is relative to path of config file. This node is OPTIONAL.
* `operations` ... specifies the list of operations, which is used on `input`. This node is OPTIONAL.
* `operation` ... specifies the operation:

#### Supported operations

* `removeNode` ... recursively removes all nodes of given name (attr. `nodeName`). Using case sensitive comparison.
* `replaceValue` ... replaces old value (attr. `oldValue`) with new value (attr. `newValue`) at all nodes. Using case sensitive comparison.
* 
All of listed attributes are required like presents the example of config file.
