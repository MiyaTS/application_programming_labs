package com.my.game_room.service;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;
import com.my.game_room.dao.Dao;
import com.my.game_room.model.GameRoom;
import com.my.game_room.model.toy.AbstractToy;
import com.my.game_room.model.toy.Car;
import com.my.game_room.model.toy.Doll;
import com.my.game_room.util.Reader;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameRoomServiceTest {

    GameRoomService gameRoomService;

    @Mock
    FilterService filterService;
    @Mock
    Dao<AbstractToy> toyDao;
    @Mock
    Dao<GameRoom> gameRoomDao;

    GameRoom gameRoom;
    Car starterMediumCar;
    Doll starterSmallDoll;

    @BeforeEach
    public void setup() {
        gameRoomService = new GameRoomService(filterService, gameRoomDao, toyDao);
        starterMediumCar = new Car(0L, "Text", "Text", 100.4, SizeType.MEDIUM, AgeCategory.TEEN, 100.4, "Text", "Text");
        starterSmallDoll = new Doll(1L, "Text", "Text", 100.4, SizeType.SMALL, AgeCategory.ADULT, "Text", "Text", "Text");
        gameRoom = new GameRoom(
                1L, "RoomName", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
    }

    @Test
    void copyRoom() {
        var room = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLine).thenReturn("RoomName");
            gameRoomService.copyRoom(gameRoom.getId());
            Assertions.assertEquals(gameRoom, room);
            Mockito.verify(gameRoomDao).save(room);
        }
    }

    @Test
    void copyRoom_noRoomFound_shouldThrowException() {
        Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> gameRoomService.copyRoom(1L));
    }

    @Test
    void copyRoom_incorrectRenaming_shouldNotSaveObject() {
        String name = "sameName";
        var room = new GameRoom(
                1L, name, "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLine).thenReturn(name);
            gameRoomService.copyRoom(gameRoom.getId());
            Assertions.assertNotEquals(gameRoom, room);
            Mockito.verifyNoMoreInteractions(gameRoomDao);
        }
    }

    @Test
    void clearRoom() {
        var room = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        var expectedRoom = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, new ArrayList<>(),
                new ArrayList<>());
        gameRoomService.clearRoom(room);
        Assertions.assertEquals(expectedRoom, room);
    }

    @Test
    void viewRooms_oneRoomListed() {
        var room = new GameRoom(
                1L, "RoomName", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        Mockito.when(gameRoomDao.getAll()).thenReturn(List.of(room));
        var isEmpty = gameRoomService.viewRooms();
        Assertions.assertFalse(isEmpty);
    }

    @Test
    void viewRooms_noRoomAvailable() {
        Mockito.when(gameRoomDao.getAll()).thenReturn(new ArrayList<>());
        var isEmpty = gameRoomService.viewRooms();
        Assertions.assertTrue(isEmpty);
    }

    @Test
    void create() {
        var expectedRoom = new GameRoom(
                0L, "RoomName", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar, starterMediumCar));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt)
                    .thenReturn(1)  //Add toy
                    .thenReturn(1)  //Find by params
                    .thenReturn(2)  //Find by Size
                    .thenReturn(1)  //Add toy
                    .thenReturn(2)  //Find manual
                    .thenReturn(0);  //Exit
            reader.when(Reader::getLong)
                    .thenReturn(1L);
            reader.when(Reader::getLine)
                    .thenReturn(gameRoom.getName())
                    .thenReturn(gameRoom.getDescription());
            reader.when(Reader::getDouble)
                    .thenReturn(gameRoom.getTotalPrice());
            reader.when(Reader::readSize)
                    .thenReturn(gameRoom.getSize());
            reader.when(Reader::getAgeCategories)
                    .thenReturn(List.of(AgeCategory.ADULT, AgeCategory.TEEN));
            reader.when(Reader::readToyType)
                    .thenReturn(ToyType.CAR);
            Mockito.when(filterService.filterByType(ToyType.CAR))
                    .thenReturn(List.of(starterMediumCar));
            Mockito.when(filterService.filterBySize(gameRoom.getSize(), List.of(starterMediumCar)))
                    .thenReturn(List.of(starterMediumCar));
            Mockito.when(toyDao.getById(1L))
                    .thenReturn(Optional.of(starterMediumCar));
            gameRoomService.create();
            Mockito.verify(gameRoomDao).save(expectedRoom);
        }
    }

    @Test
    void edit_clearRoom() {
        var room = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        var expectedRoom = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, new ArrayList<>(),
                new ArrayList<>());
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
            reader.when(Reader::getInt)
                    .thenReturn(1);
            gameRoomService.edit(1L);
            Assertions.assertEquals(expectedRoom, room);
            Mockito.verify(gameRoomDao).update(expectedRoom);
        }
    }

    @Test
    void edit_renameRoom() {
        var room = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
            reader.when(Reader::getInt)
                    .thenReturn(2);
            reader.when(Reader::getLine).thenReturn("RoomName");
            gameRoomService.edit(1L);
            Assertions.assertEquals(gameRoom, room);
            Mockito.verify(gameRoomDao).update(gameRoom);
        }
    }

    @Test
    void edit_incorrectChoice() {
        var room = new GameRoom(
                1L, "", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
            Mockito.verifyNoMoreInteractions(gameRoomDao);
            reader.when(Reader::getInt)
                    .thenReturn(10);
            gameRoomService.edit(1L);
        }
    }

    @Test
    void edit_manualFullEditing() {
        List<AbstractToy> toyList = new ArrayList<>();
        toyList.add(starterMediumCar);
        toyList.add(starterMediumCar);
        var room = new GameRoom(
                1L, "TEXT", "TEXT", 230.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                toyList);
        var expectedRoom = new GameRoom(
                1L, "RoomName", "Changed descr", 230.0,
                SizeType.HUGE, List.of(AgeCategory.ADULT),
                List.of(starterMediumCar));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
            reader.when(Reader::getInt)
                    .thenReturn(3)  //Manual Editing
                    .thenReturn(1)  //Change name
                    .thenReturn(2)  //Change descr
                    .thenReturn(3)  //Change size
                    .thenReturn(4)  //Change ages
                    .thenReturn(5)  //Change toys
                    .thenReturn(1)  //Remove last car
                    .thenReturn(20)  //Set incorrect index
                    .thenReturn(2)  //Set index to remove
                    .thenReturn(0)  //Save and exit
            ;
            reader.when(Reader::getLine)
                    .thenReturn("RoomName")
                    .thenReturn("Changed descr");
            reader.when(Reader::readSize)
                    .thenReturn(SizeType.HUGE);
            reader.when(Reader::getAgeCategories)
                    .thenReturn(List.of(AgeCategory.ADULT));
            gameRoomService.edit(1L);
            Mockito.verify(gameRoomDao).update(expectedRoom);
        }
    }

    @Test
    void delete() {
        var id = 1L;
        gameRoomService.delete(id);
        Mockito.verify(gameRoomDao).delete(id);
    }

    @Test
    void viewInfo_roomFound() {
        var room = new GameRoom(
                1L, "RoomName", "", 0.0,
                SizeType.BIG, List.of(AgeCategory.ADULT, AgeCategory.TEEN),
                List.of(starterMediumCar));
        Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.of(room));
        gameRoomService.viewInfo(gameRoom.getId());
        Assertions.assertEquals(gameRoom, room);
    }

    @Test
    void viewInfo_noRoomFound_shouldThrowException() {
        Mockito.when(gameRoomDao.getById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> gameRoomService.copyRoom(1L));
    }
}