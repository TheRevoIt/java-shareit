package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.util.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByBookerIdAndItemIdAndEndBeforeOrderByStartDesc(Long bookerId, long itemId, LocalDateTime end);

    List<Booking> findBookingsByBookerIdOrderByStartDesc(long userId);

    List<Booking> findBookingsByBookerIdAndStartAfter(long userId, LocalDateTime end, Sort sort);

    List<Booking> findBookingsByBookerIdAndEndBefore(long userId, LocalDateTime now, Sort sort);

    @Query("select b from Booking b where :time between b.start " +
            "and b.end and b.booker.id = :bookerId order by b.start desc")
    List<Booking> findBookingsByBookerIdCurrent(@Param("bookerId") long userId, @Param("time") LocalDateTime time);

    @Query("select b from Booking b where :time between b.start " +
            "and b.end and b.item.owner.id = :ownerId order by b.start desc")
    List<Booking> findBookingsByItemOwnerCurrent(@Param("ownerId") long userId, @Param("time") LocalDateTime time);

    List<Booking> findBookingsByItemOwnerIdAndEndBefore(long userId, LocalDateTime now, Sort sort);

    List<Booking> findBookingsByItemOwnerIdOrderByStartDesc(long userId);

    List<Booking> findBookingsByItemOwnerIdAndStartAfter(long userId, LocalDateTime end, Sort sort);

    @Query("select distinct b " +
            "from Booking b " +
            "where b.start <= :now " +
            "and b.item.id in :itemIds " +
            "and b.bookingStatus = 'APPROVED'" +
            "and b.item.owner.id = :userId " +
            "order by b.start desc ")
    List<Booking> findBookingsByListOfItemsLast(@Param("itemIds") List<Long> itemIds,
                                                @Param("now") LocalDateTime now,
                                                @Param("userId") long userId);

    @Query("select distinct b " +
            "from Booking b " +
            "where b.start > :now " +
            "and b.bookingStatus = 'APPROVED'" +
            "and b.item.id in :itemIds " +
            "and b.item.owner.id = :userId " +
            "order by b.start asc ")
    List<Booking> findBookingsByListOfItemsNext(@Param("itemIds") List<Long> itemIds,
                                                @Param("now") LocalDateTime now,
                                                @Param("userId") long userId);

    List<Booking> findBookingByBookerIdAndBookingStatusOrderByStartDesc(long userId, BookingStatus status);

    List<Booking> findBookingByItemOwnerIdAndBookingStatusOrderByStartDesc(long ownerId, BookingStatus status);
}
