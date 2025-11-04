-- Insira antes de testar endpoints de cursos
INSERT INTO courses (
    course_id, title, summary, description, COURSE_LEVEL, thumbnail
) VALUES (
             'a12f5670-2d4e-11ef-bf4b-123456789abc',
             'Introdução aos Investimentos',
             'Aprenda os fundamentos essenciais para começar a investir com segurança.',
             'Curso completo para aprender sobre tipos de investimento, riscos e rentabilidade.',
             'BASICO',
             'https://cdn.borainvestir.b3.com.br/2024/04/17112249/fundos-de-investimentos-como-escolher.jpg'
         );

INSERT INTO lessons (
    lesson_id, course_id, title, video_url, duration_in_sec, lesson_order
) VALUES (
             '63190b5a-08c2-4b77-87e0-d71b4286367f',
             'a12f5670-2d4e-11ef-bf4b-123456789abc',
             'O que é investimento?',
             'https://youtu.be/Mca-HLZkaB8?si=95M8eDyMHJJMHl8R',
             241,
             1
         );

INSERT INTO lessons (
    lesson_id, course_id, title, video_url, duration_in_sec, lesson_order
) VALUES (
             '36ef136e-5728-4dc2-a0e6-9d5730e7d81c',
             'a12f5670-2d4e-11ef-bf4b-123456789abc',
             'Perfil do investidor',
             'https://youtu.be/enDUY7pHwsU?si=66az5prpSvP-UF3z',
             543,
             2
         );
