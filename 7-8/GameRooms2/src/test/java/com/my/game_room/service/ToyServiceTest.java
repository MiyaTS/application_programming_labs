package com.my.game_room.service;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;
import com.my.game_room.dao.Dao;
import com.my.game_room.model.toy.*;
import com.my.game_room.util.Reader;
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

@ExtendWith(MockitoExtension.class)
class ToyServiceTest {

    ToyService toyService;

    @Mock
    FilterService filterService;

    @Mock
    Dao<AbstractToy> toyDao;

    List<AbstractToy> starterList;
    Car starterMediumCar;
    Doll starterSmallDoll;
    Cubes starterHugeCubes;
    Musical starterBigMusical;

    @BeforeEach
    public void setup() {
        toyService = new ToyService(filterService, toyDao);
        starterMediumCar = new Car(0L, "Text", "Text", 100.4, SizeType.MEDIUM, AgeCategory.TEEN, 100.4, "Text", "Text");
        starterSmallDoll = new Doll(1L, "Text", "Text", 100.4, SizeType.SMALL, AgeCategory.ADULT, "Text", "Text", "Text");
        starterHugeCubes = new Cubes(2L, "Text", "Text", SizeType.HUGE, AgeCategory.BABY, 20, 20.0, 120.5);
        starterBigMusical = new Musical(3L, "Text", "Text", 100.4, SizeType.BIG, AgeCategory.TODDLER, true, "Text", "Text");
        starterList = List.of(
                starterMediumCar,
                new Car(2L, "Name", "Big Descr", 302.4, SizeType.HUGE, AgeCategory.TEEN, 204.1, "Silver", "Blue")
        );
    }

    @Test
    void deleteToysByPrice_deleteWasCalledForList() {
        Mockito.when(toyDao.getAll()).thenReturn(starterList);
        Mockito.when(filterService.filterByPrice(starterList)).thenReturn(starterList);
        toyService.deleteToysByPrice();
        Mockito.verify(toyDao, Mockito.times(1)).delete(0L);
        Mockito.verify(toyDao, Mockito.times(1)).delete(2L);
    }

    @Test
    void deleteToysByPrice_noToyDeleted() {
        Mockito.when(toyDao.getAll()).thenReturn(starterList);
        Mockito.when(filterService.filterByPrice(starterList)).thenReturn(new ArrayList<>());
        toyService.deleteToysByPrice();
        Mockito.verify(toyDao).getAll();
        Mockito.verifyNoMoreInteractions(toyDao);
    }

    @Test
    void create_car() {
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::readToyType).thenReturn(ToyType.CAR);
            reader.when(Reader::getLine).thenReturn("Text");
            reader.when(Reader::getDouble).thenReturn(100.4);
            reader.when(Reader::readSize).thenReturn(SizeType.MEDIUM);
            reader.when(Reader::readAge).thenReturn(AgeCategory.TEEN);
            toyService.create();
            Mockito.verify(toyDao).save(starterMediumCar);
        }
    }

    @Test
    void create_doll() {
        var expectedDoll = new Doll(0L, "Text", "Text", 100.4, SizeType.SMALL, AgeCategory.ADULT, "Text", "Text", "Text");
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::readToyType).thenReturn(ToyType.DOLL);
            reader.when(Reader::getLine).thenReturn("Text");
            reader.when(Reader::getDouble).thenReturn(100.4);
            reader.when(Reader::readSize).thenReturn(SizeType.SMALL);
            reader.when(Reader::readAge).thenReturn(AgeCategory.ADULT);
            toyService.create();
            Mockito.verify(toyDao).save(expectedDoll);
        }
    }

    @Test
    void create_cubes() {
        var expectedCubes = new Cubes(0L, "Text", "Text", SizeType.HUGE, AgeCategory.BABY, 30, 100.4, 100.4);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::readToyType).thenReturn(ToyType.CUBES);
            reader.when(Reader::getLine).thenReturn("Text");
            reader.when(Reader::getInt).thenReturn(30);
            reader.when(Reader::getDouble).thenReturn(100.4);
            reader.when(Reader::readSize).thenReturn(SizeType.HUGE);
            reader.when(Reader::readAge).thenReturn(AgeCategory.BABY);
            toyService.create();
            Mockito.verify(toyDao).save(expectedCubes);
        }
    }

    @Test
    void create_musical() {
        var expectedMusical = new Musical(0L, "Text", "Text", 100.4, SizeType.BIG, AgeCategory.TODDLER, false, "Text", "Text");
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::readToyType).thenReturn(ToyType.MUSICAL);
            reader.when(Reader::getLine).thenReturn("Text");
            reader.when(Reader::getDouble).thenReturn(100.4);
            reader.when(Reader::getBoolean).thenReturn(false);
            reader.when(Reader::readSize).thenReturn(SizeType.BIG);
            reader.when(Reader::readAge).thenReturn(AgeCategory.TODDLER);
            toyService.create();
            Mockito.verify(toyDao).save(expectedMusical);
        }
    }

    @Test
    void edit_carWithAllParams() {
        var toy = new Car(0L, "", "", 100.4, SizeType.MEDIUM, AgeCategory.TEEN, 0, "", "");
        Mockito.when(toyDao.getById(toy.getId())).thenReturn(Optional.of(toy));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLong)
                    .thenReturn(starterMediumCar.getId());
            reader.when(Reader::getInt)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0);
            reader.when(Reader::getLine)
                    .thenReturn(starterMediumCar.getName())
                    .thenReturn(starterMediumCar.getDescription())
                    .thenReturn(starterMediumCar.getMaterial())
                    .thenReturn(starterMediumCar.getColor());
            reader.when(Reader::readSize).thenReturn(starterMediumCar.getSize());
            reader.when(Reader::getDouble).thenReturn(starterMediumCar.getSpeed());
            toyService.edit();
            Mockito.verify(toyDao).update(toy);
            var actualToy = toyService.viewInfo(toy.getId());
            Assertions.assertEquals(starterMediumCar, actualToy);
        }
    }

    @Test
    void edit_dollWithAllParams() {
        var toy = new Doll(1L, "", "", 100.4, SizeType.SMALL, AgeCategory.ADULT, "", "", "");
        Mockito.when(toyDao.getById(toy.getId())).thenReturn(Optional.of(toy));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLong)
                    .thenReturn(starterSmallDoll.getId());
            reader.when(Reader::getInt)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0);
            reader.when(Reader::getLine)
                    .thenReturn(starterSmallDoll.getName())
                    .thenReturn(starterSmallDoll.getDescription())
                    .thenReturn(starterSmallDoll.getHairColor())
                    .thenReturn(starterSmallDoll.getSpecialAbility())
                    .thenReturn(starterSmallDoll.getJewellery());
            reader.when(Reader::readSize).thenReturn(starterSmallDoll.getSize());
            toyService.edit();
            Mockito.verify(toyDao).update(toy);
            var actualToy = toyService.viewInfo(toy.getId());
            Assertions.assertEquals(starterSmallDoll, actualToy);
        }
    }

    @Test
    void edit_cubesWithAllParams() {
        var toy = new Cubes(2L, "", "", SizeType.HUGE, AgeCategory.BABY, 0, 0.0, 0);
        Mockito.when(toyDao.getById(toy.getId())).thenReturn(Optional.of(toy));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLong)
                    .thenReturn(starterHugeCubes.getId());
            reader.when(Reader::getInt)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0)
                    .thenReturn(1)
                    .thenReturn(starterHugeCubes.getAmount())
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0);
            reader.when(Reader::getLine)
                    .thenReturn(starterHugeCubes.getName())
                    .thenReturn(starterHugeCubes.getDescription());
            reader.when(Reader::readSize).thenReturn(starterHugeCubes.getSize());
            reader.when(Reader::getDouble).thenReturn(starterHugeCubes.getPriceByOne()).thenReturn(starterHugeCubes.getWeight());
            toyService.edit();
            Mockito.verify(toyDao).update(toy);
            var actualToy = toyService.viewInfo(toy.getId());
            Assertions.assertEquals(starterHugeCubes, actualToy);
        }
    }

    @Test
    void edit_musicalWithAllParams() {
        var toy = new Musical(3L, "", "", 100.4, SizeType.BIG, AgeCategory.TODDLER, false, "", "");
        Mockito.when(toyDao.getById(toy.getId())).thenReturn(Optional.of(toy));
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getLong)
                    .thenReturn(starterBigMusical.getId());
            reader.when(Reader::getInt)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0)
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(0);
            reader.when(Reader::getLine)
                    .thenReturn(starterBigMusical.getName())
                    .thenReturn(starterBigMusical.getDescription())
                    .thenReturn(starterBigMusical.getMusicType())
                    .thenReturn(starterBigMusical.getMaterial());
            reader.when(Reader::readSize).thenReturn(starterBigMusical.getSize());
            reader.when(Reader::getBoolean).thenReturn(starterBigMusical.isAutoPlayEnabled());
            toyService.edit();
            Mockito.verify(toyDao).update(toy);
            var actualToy = toyService.viewInfo(toy.getId());
            Assertions.assertEquals(starterBigMusical, actualToy);
        }
    }

    @Test
    void delete() {
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::readToyType).thenReturn(ToyType.CAR);
            Mockito.when(filterService.filterByType(ToyType.CAR)).thenReturn(starterList);
            reader.when(Reader::getInt).thenReturn(1);
            toyService.delete();
            Mockito.verify(filterService).filterByType(ToyType.CAR);
            Mockito.verify(toyDao).delete(1);
        }
    }

    @Test
    void viewInfo_noToyFound_thenThrownException() {
        Mockito.when(toyDao.getById(starterMediumCar.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> toyService.viewInfo(starterMediumCar.getId()));
    }

    @Test
    void viewInfo_toyFound() {
        Mockito.when(toyDao.getById(starterMediumCar.getId())).thenReturn(Optional.of(starterMediumCar));
        var toy = toyService.viewInfo(starterMediumCar.getId());
        Assertions.assertEquals(starterMediumCar, toy);
    }

    @Test
    void viewAllToys_toysFound() {
        Mockito.when(filterService.getAllToys()).thenReturn(starterList);
        boolean isEmpty = toyService.viewAllToys();
        Assertions.assertFalse(isEmpty);
    }

    @Test
    void viewAllToys_noToysFound() {
        Mockito.when(filterService.getAllToys()).thenReturn(new ArrayList<>());
        boolean isEmpty = toyService.viewAllToys();
        Assertions.assertTrue(isEmpty);
    }

}