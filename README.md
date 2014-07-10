# xml-tree-editor-siemens

This project is written to prove my Java SE 7 skills for hiring management of [Siemens Corp](https://jobsearch.siemens.biz/career?career%5fns=job%5flisting&company=Siemens&navBarLevel=JOB%5fSEARCH&rcm%5fsite%5flocale=cs%5fCZ&career_job_req_id=173389&selected_lang=cs_CZ&jobAlertController_jobAlertId=&jobAlertController_jobAlertName=&_s.crb=Yg7P8r7oV1GsVgA9c5OzqlL54k0%3d).


## General Requirements

## Usage

``` bash
java -jar XmlTreeEditor.jar <CONFIG_FILE_PATH>
```
where 
* `CONFIG_FILE_PATH` ... is file path to a config file which is described here

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
* `schema` ... specifies the schema (XSD) file path of `input`, which is relative to path of config file. This node is OPTION.
* `operations` ... specifies the list of operations, which is used on `input`. This node is OPTION.
* `operation` ... specifies the operation:

#### Supported operations

* `removeNode` ... recursively removes all nodes of given name (attr. `nodeName`). Using case sensitive comparation.
* `replaceValue` ... replaces old value (attr. `oldValue`) with new value (attr. `newValue`) at all nodes. Using case sensitive comparation.

All of presented attributes are required like the example of config file presents.
