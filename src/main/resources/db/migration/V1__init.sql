create table country
(
    calling_code integer not null
        constraint country_pkey
            primary key
);

create table processed_log
(
    date                                   date      not null
        constraint processed_log_pkey
            primary key,
    processed_at                          timestamp not null,
    number_of_calls                       integer   not null,
    number_of_ko_calls                    integer   not null,
    number_of_messages                    integer   not null,
    number_of_messages_with_blank_content integer   not null,
    number_of_ok_calls                    integer   not null,
    number_of_rows                        integer   not null,
    number_of_rows_with_field_errors      integer   not null,
    number_of_rows_with_missing_fields    integer   not null,
    process_duration                      bigint    not null
);

create table processed_log_country_call_statistics
(
    processed_log_id     date             not null
        constraint fkcyqp43xnaivxjeacmp1qplawb
            references processed_log
            on update cascade
            on delete restrict,
    call_to_country_id    integer          not null
        constraint fklaow9eb6vnqa0e5dlbqag2hxv
            references country
            on update cascade
            on delete restrict,
    call_from_country_id  integer          not null
        constraint fkmx0jw51pvaxm44d5rps680o8x
            references country
            on update cascade
            on delete restrict,

    average_call_duration double precision not null,
    count_of_calls        integer          not null,
    constraint processed_log_country_call_statistics_pkey
        primary key (call_from_country_id, call_to_country_id,
                     processed_log_id)
);

create table processed_log_word_occurrence
(
    word                  varchar(255) not null,
    processed_log_id     date         not null
        constraint fko3k0gk8alvg6jogcqwqbf2jna
            references processed_log
            on update cascade
            on delete restrict,
    number_of_occurrences integer      not null,
    constraint processed_log_word_occurrence_pkey
        primary key (processed_log_id, word)
);



