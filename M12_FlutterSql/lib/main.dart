import 'dart:io'; // support for Directory

import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart'; // for getApplicationDocumentDirectory()



// run main
main() {
  WidgetsFlutterBinding.ensureInitialized(); //used to interact with the flutter engine

  runApp(SqliteApp());
}

class SqliteApp extends StatefulWidget {
  const SqliteApp({Key? key}) : super(key: key);

  @override
  _SqliteAppState createState() => _SqliteAppState();
}



class _SqliteAppState extends State<SqliteApp> {
  final textController = TextEditingController(); //for text field, new TextEditingController()
// end run main



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
      appBar: AppBar(
        title: TextField(
          controller: textController, // add the textController object to the app bar
        ),
      ),


      body: Center(
        child: FutureBuilder<List<Character>>( // wait for data
            future: DatabaseHelper.instance.getCharacters(), //get this data
            builder:
                (BuildContext context, AsyncSnapshot<List<Character>> snapshot) {
              if (!snapshot.hasData) {
                return Center(child: Text('Loading...')); // if data but not yet displayed,
              }
              return snapshot.data!.isEmpty
                  ? Center(child: Text('No Characters in List')) // if no data
                  : ListView( // view for data
                      children: snapshot.data!.map((character) {
                        return Center(
                          child: ListTile(
                            title: Text(character.name),
                          ),
                        );
                      }).toList(),
                    );
            }),
      ),


      floatingActionButton: FloatingActionButton( // FAB
        child: Icon(Icons.catching_pokemon), // with pokemon icon
        onPressed: () async { // when pressing the button, add to the DB the contents of the textController to the character
            await DatabaseHelper.instance.add(
              Character(name: textController.text), //Character object sets name as contents of textController
            );
            setState((){
              textController.clear();
            });
        }),



    ),
  );
  }
}





class Character {
  final int? id; // ? id can't be null
  final String name;

  Character({this.id, required this.name});

  //constructor
  //factory to make object a singleton pattern- struct doesnt' always create new instance of class

  // getter
  factory Character.fromMap(Map<String, dynamic> json) => new Character(
        id: json['id'],
        name: json['name'],
      );

  //setter
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
    };
  }
}









class DatabaseHelper {
  DatabaseHelper._privateConstructor();
  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();

  //setting up database
  static Database?
      _database; //if database doesnt exists, then initialize database with onCreate method
  Future<Database> get database async => _database ??= await _initDatabase();

  //create database
  //opens database as version 1. if doesnt exists, run on create
  Future<Database> _initDatabase() async {
    Directory documentsDirectory = await getApplicationDocumentsDirectory(); // this is the pathfinder method, used to find the file path no matter the phone
    String path = join(documentsDirectory.path, 'characters.db');
    return await openDatabase(
      path,
      version: 1,
      onCreate: _onCreate,
    );
  }

  Future _onCreate(Database db, int version) async {
    // create table
    await db.execute('''
    CREATE TABLE characters (id INTERGER PRIMARY KEY, name TEXT)
    ''');
  }

  //get characters and display in a list
  Future<List<Character>> getCharacters() async {
    Database db = await instance.database;
    var characters = await db.query('characters', orderBy: 'name');
    List<Character> characterList = characters.isNotEmpty
        ? characters.map((c) => Character.fromMap(c)).toList()
        : [];
    return characterList;
  }

//add to db
  Future<int> add(Character
  character) async {
    Database db = await instance.database;
    return await db.insert('characters', character.toMap());
}
}



// Async means that this function is asynchronous and you might need to wait a bit to get its result.
// Await literally means - wait here until this function is finished and you will get its return value.
// Future is a type that ‘comes from the future’ and returns value from your asynchronous function. It can complete with success(.then) or with
// an error(.catchError).