# Employee Management with JList (Serialization & Exceptions Practice)
My second Java project on GitHub that spiritually improves on my previous one. 
It applies the MVC design philosophy more decisively by structuring and dividing the project in Model, Controller and View packages.

<p align="center">
  <h1><ins>General Features</ins><h2>

  <h2>Synchronous JList and data structure navigation:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/fad85101-4b34-4f56-9372-03585d47beb0" /><br>
  <h2>Creation of new employees with meticulous error handling and use of JDatePicker:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/fb744b0b-b32f-4bf2-a81c-c89c0ab9b32d" /><br>
  <h2>Massive creation of dummy employees:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/c7866d8d-fcb7-41c1-9e88-a71ba8e82657" /><br>
  <h2>Sorting of the employees through a manually implemented bubble sort and a native Collection library sort:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/b5bff205-257f-44ac-a254-4619d5f14e2c" /><br>
  <h2>Data serializing as well as deserializing using JFileChooser:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/0267be80-2991-4756-b774-9151e7e6ae60" /><br>
  <h2>Name encryption on serializing/deserializing:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/572a3a46-c222-467d-ac3d-a04b750bd729" /><br>
  <h2>Calculating and adding annual bonuses and monthly extras to their salaries:</h2><br>
  <img src="https://github.com/MaBerGal/Employee_Management_with_JList/assets/148444718/9b8219bc-d468-47de-8170-962ca07ac517" /><br>
</p>

### Version History
* _V1.0:_
  - Added support for navigating through employees' data, and visualizing, saving and loading it.
  - Added support for creating new employee objects of different types.
  - Added support for updating an employee's salary based on their pending bonuses.
  - Added support for en masse sample data generation and data sorting.

### Known Bugs
* _JList causing the program to crash:_
  - If an item has been selected in the JList (i.e. clicked) and the user attempts to perform certain actions, the program may crash.
    The error has somewhat been tracked to the _updateJList()_ method, which implements _getCurrent()_ from the list controller.
    However, it might have to do with JList's internal logic.
