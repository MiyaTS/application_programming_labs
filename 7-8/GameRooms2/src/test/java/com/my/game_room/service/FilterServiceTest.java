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

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

    FilterService filterService;

    @Mock
    Dao<AbstractToy> toyDao;

    List<AbstractToy> starterList;
    AbstractToy starterMediumCar;
    Doll starterSmallDoll;
    Cubes starterHugeCubes;
    Musical starterBigMusical;

    @BeforeEach
    public void setup() {
        filterService = new FilterService(toyDao);
        starterMediumCar = new Car(2L, "Name2", "Big Desc2r", 100.4, SizeType.MEDIUM, AgeCategory.TEEN, 404.1, "Silver", "Blue");
        starterSmallDoll = new Doll(1L, "Text", "Text", 100.4, SizeType.SMALL, AgeCategory.ADULT, "Text", "Text", "Text");
        starterHugeCubes = new Cubes(2L, "Text", "Text", SizeType.HUGE, AgeCategory.BABY, 20, 20.0, 120.5);
        starterBigMusical = new Musical(3L, "Text", "Text", 100.4, SizeType.BIG, AgeCategory.TODDLER, true, "Text", "Text");
        starterList = List.of(
                starterMediumCar,
                new Car(1L, "Name", "Big Descr", 302.4, SizeType.HUGE, AgeCategory.TEEN, 204.1, "Silver", "Blue")
        );
    }

    @Test
    void getAllToys_availableInDatabase() {
        Mockito.when(toyDao.getAll()).thenReturn(starterList);
        List<AbstractToy> actualList = filterService.getAllToys();
        Assertions.assertEquals(starterList, actualList);
    }

    @Test
    void getAllToys_noToysInDatabase() {
        Mockito.when(toyDao.getAll()).thenReturn(new ArrayList<>());
        List<AbstractToy> actualList = filterService.getAllToys();
        Assertions.assertEquals(new ArrayList<>(), actualList);
    }

    @Test
    void filterByType_filteredToCarList() {
        Mockito.when(toyDao.getAll()).thenReturn(starterList);
        List<AbstractToy> actualCarList = filterService.filterByType(ToyType.CAR);
        Assertions.assertEquals(starterList, actualCarList);
    }

    @Test
    void filterByType_filteredToDollEmptyList() {
        Mockito.when(toyDao.getAll()).thenReturn(starterList);
        List<AbstractToy> actualEmptyDollList = filterService.filterByType(ToyType.DOLL);
        Assertions.assertNotEquals(starterList, actualEmptyDollList);
        Assertions.assertEquals(new ArrayList<>(), actualEmptyDollList);
    }

    @Test
    void filterByPrice_priceMoreThan() {
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(1);
            reader.when(Reader::getDouble).thenReturn(100.0);
            var actualList = filterService.filterByPrice(starterList);
            Assertions.assertEquals(starterList, actualList);
        }
    }

    @Test
    void filterByPrice_priceLessThan() {
        List<AbstractToy> expectedList = List.of(starterMediumCar);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(2);
            reader.when(Reader::getDouble).thenReturn(200.0);
            var actualList = filterService.filterByPrice(starterList);
            Assertions.assertEquals(expectedList, actualList);
        }
    }

    @Test
    void filterBySize_withSmall() {
        List<AbstractToy> actualList = List.of(
                starterMediumCar,
                starterSmallDoll,
                starterBigMusical,
                starterHugeCubes
        );
        List<AbstractToy> expectedList = List.of(starterSmallDoll);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(1);
            var resultList = filterService.filterBySize(SizeType.SMALL, actualList);
            Assertions.assertEquals(expectedList, resultList);
        }
    }

    @Test
    void filterBySize_withMedium() {
        List<AbstractToy> actualList = List.of(
                starterMediumCar,
                starterSmallDoll,
                starterBigMusical,
                starterHugeCubes
        );
        List<AbstractToy> expectedList = List.of(starterMediumCar);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(2);
            var resultList = filterService.filterBySize(SizeType.MEDIUM, actualList);
            Assertions.assertEquals(expectedList, resultList);
        }
    }

    @Test
    void filterBySize_withBig() {
        List<AbstractToy> actualList = List.of(
                starterMediumCar,
                starterSmallDoll,
                starterBigMusical,
                starterHugeCubes
        );
        List<AbstractToy> expectedList = List.of(starterBigMusical);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(3);
            var resultList = filterService.filterBySize(SizeType.BIG, actualList);
            Assertions.assertEquals(expectedList, resultList);
        }
    }

    @Test
    void filterBySize_withHuge() {
        List<AbstractToy> actualList = List.of(
                starterMediumCar,
                starterSmallDoll,
                starterBigMusical,
                starterHugeCubes
        );
        List<AbstractToy> expectedList = List.of(starterHugeCubes);
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(4);
            var resultList = filterService.filterBySize(SizeType.HUGE, actualList);
            Assertions.assertEquals(expectedList, resultList);
        }
    }

    @Test
    void filterBySize_noToys() {
        List<AbstractToy> actualList = List.of(
                starterBigMusical,
                starterHugeCubes
        );
        try (MockedStatic<Reader> reader = Mockito.mockStatic(Reader.class)) {
            reader.when(Reader::getInt).thenReturn(4);
            var resultList = filterService.filterBySize(SizeType.SMALL, actualList);
            Assertions.assertEquals(new ArrayList<>(), resultList);
        }
    }

    @Test
    void filterByAge_ToysFound() {
        var actualList = filterService.filterByAge(List.of(AgeCategory.TEEN), starterList);
        Assertions.assertEquals(starterList, actualList);
    }

    @Test
    void filterByAge_noAgesFound() {
        List<AbstractToy> expectedList = new ArrayList<>();
        var actualList = filterService.filterByAge(List.of(AgeCategory.ADULT), starterList);
        Assertions.assertEquals(expectedList, actualList);
    }
}